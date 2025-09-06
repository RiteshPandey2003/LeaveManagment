package com.example.Leave.Managment.service;

import com.example.Leave.Managment.DTO.EmployeeDTO;
import com.example.Leave.Managment.DTO.LeaveBalanceDTO;
import com.example.Leave.Managment.DTO.LeaveRequestDTO;
import com.example.Leave.Managment.DTO.LeaveTypeDTO;
import com.example.Leave.Managment.entity.tables.LeaveBalance;
import com.example.Leave.Managment.entity.tables.LeaveRequest;
import com.example.Leave.Managment.repository.EmployeeRepository;
import com.example.Leave.Managment.repository.LeaveBalanceRepository;
import com.example.Leave.Managment.repository.LeaveRequestRepository;
import com.example.Leave.Managment.repository.LeaveTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    public List<LeaveRequestDTO> getAllRequests(Long employeeId) {
        return leaveRequestRepository.findRequestsWithBalance(employeeId).stream()
                .map(obj -> {
                    LeaveRequest request = (LeaveRequest) obj[0];
                    LeaveBalance balance = (LeaveBalance) obj[1]; // may be null

                    EmployeeDTO employeeDTO = new EmployeeDTO(
                            request.getEmployee().getId(),
                            request.getEmployee().getName(),
                            request.getEmployee().getEmail(),
                            request.getEmployee().getDepartment(),
                            request.getEmployee().getRole(),
                            request.getEmployee().getJoinDate()
                    );

                    LeaveTypeDTO leaveTypeDTO = new LeaveTypeDTO(
                            request.getLeaveType().getLeaveId(),
                            request.getLeaveType().getName().name(),
                            request.getLeaveType().getDescription(),
                            request.getLeaveType().getMaxDays()
                    );

                    LeaveRequestDTO dto = new LeaveRequestDTO(
                            request.getRequestId(),
                            employeeDTO,
                            leaveTypeDTO,
                            request.getStartDate(),
                            request.getEndDate(),
                            request.getStatus().toString(),
                            request.getAppliedOn()
                    );

                    // If balance exists, attach it
                    if (balance != null) {
                        LeaveBalanceDTO balanceDTO = new LeaveBalanceDTO(
                                balance.getBalanceId(),
                                employeeDTO,
                                leaveTypeDTO,
                                balance.getUsedLeave()
                        );
                        dto.getLeaveType().setName(
                                leaveTypeDTO.getName() + " (Balance: " + balanceDTO.getRemainingDays() + " days)"
                        );
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public String applyLeave(Long employeeId, Long leaveTypeId, Date startDate, Date endDate) {
        // 1. Fetch existing LeaveBalance
        LeaveBalance balance = leaveBalanceRepository.findByEmployeeAndLeaveType(employeeId, leaveTypeId);

        // 2. If balance record doesn’t exist, create one
        if (balance == null) {
            balance = new LeaveBalance();
            balance.setEmployee(employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employee not found")));
            balance.setLeaveType(leaveTypeRepository.findById(leaveTypeId)
                    .orElseThrow(() -> new RuntimeException("Leave type not found")));
            balance.setUsedLeave(0); // start fresh
            leaveBalanceRepository.save(balance);
        }

        // 3. Calculate requested days
        long diffInMillies = endDate.getTime() - startDate.getTime();
        int requestedDays = (int) (diffInMillies / (1000 * 60 * 60 * 24)) + 1;

        // 4. Try to consume leave from balance
        if (!balance.useLeave(requestedDays)) {
            return "Not enough leave balance. Available: " + balance.getremainingDays();
        }

        // 5. Persist updated balance
        leaveBalanceRepository.save(balance);

        // 6. Save leave request
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(balance.getEmployee());
        leaveRequest.setLeaveType(balance.getLeaveType());
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setStatus(LeaveRequest.LeaveStatus.PENDING);
        leaveRequest.setAppliedOn(new Date());

        leaveRequestRepository.save(leaveRequest);

        return "Leave request submitted successfully. Pending approval. " +
                "Remaining balance: " + balance.getremainingDays() + " days.";
    }


    @Transactional
    public String updateLeaveStatus(Long requestId, String status, String role) {
        if (!"MANAGER".equalsIgnoreCase(role)) {
            return "Only managers are allowed to update leave requests.";
        }

        // Fetch request
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found."));

        // Update status
        LeaveRequest.LeaveStatus newStatus;
        try {
            newStatus = LeaveRequest.LeaveStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Invalid status. Allowed: PENDING, APPROVE, REJECT.";
        }

        leaveRequest.setStatus(newStatus);

        // If APPROVED → deduct from balance
        if (newStatus == LeaveRequest.LeaveStatus.APPROVE) {
            LeaveBalance balance = leaveBalanceRepository.findByEmployeeAndLeaveType(
                    leaveRequest.getEmployee().getId(),
                    leaveRequest.getLeaveType().getLeaveId()
            );
            if (balance != null) {
                long diffInMillies = leaveRequest.getEndDate().getTime() - leaveRequest.getStartDate().getTime();
                int requestedDays = (int) (diffInMillies / (1000 * 60 * 60 * 24)) + 1;

                if (requestedDays <= balance.getUsedLeave()) {
                    balance.setUsedLeave(balance.getUsedLeave() - requestedDays);
                    leaveBalanceRepository.save(balance);
                } else {
                    return "Approval failed. Not enough leave balance.";
                }
            }
        }

        leaveRequestRepository.save(leaveRequest);
        return "Leave status updated to " + newStatus;
    }
}
