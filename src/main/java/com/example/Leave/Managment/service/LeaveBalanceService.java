package com.example.Leave.Managment.service;

import com.example.Leave.Managment.DTO.EmployeeDTO;
import com.example.Leave.Managment.DTO.LeaveBalanceDTO;
import com.example.Leave.Managment.DTO.LeaveTypeDTO;
import com.example.Leave.Managment.repository.LeaveBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveBalanceService {

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    public List<LeaveBalanceDTO> getLeaveBalance(Long id) {
        return leaveBalanceRepository.findBalanceByEmployeeId(id).stream().map(lb -> new LeaveBalanceDTO(
                        lb.getBalanceId(),
                        new EmployeeDTO(
                                lb.getEmployee().getId(),
                                lb.getEmployee().getName(),
                                lb.getEmployee().getEmail(),
                                lb.getEmployee().getDepartment(),
                                lb.getEmployee().getRole(),
                                lb.getEmployee().getJoinDate()

                        ),
                        new LeaveTypeDTO(
                                lb.getLeaveType().getLeaveId(),
                                lb.getLeaveType().getName().name(),
                                lb.getLeaveType().getDescription(),
                                lb.getLeaveType().getMaxDays()
                        ),
                        lb.getUsedLeave()
                )).collect(Collectors.toList());

    }
}
