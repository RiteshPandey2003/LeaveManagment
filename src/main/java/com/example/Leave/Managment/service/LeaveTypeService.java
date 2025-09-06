package com.example.Leave.Managment.service;

import com.example.Leave.Managment.DTO.LeaveTypeDTO;
import com.example.Leave.Managment.entity.tables.LeaveType;
import com.example.Leave.Managment.repository.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveTypeService {

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    // ✅ Convert Entity → DTO
    public List<LeaveTypeDTO> getLeaveType() {
        return leaveTypeRepository.findAll().stream().map(leaveType ->
                new LeaveTypeDTO(
                        leaveType.getLeaveId(),
                        leaveType.getName().name(),
                        leaveType.getDescription(),
                        leaveType.getMaxDays()
                )
        ).collect(Collectors.toList());
    }

    // ✅ Convert DTO → Entity
    public LeaveTypeDTO updateLeaveType(String leaveCategory, LeaveTypeDTO leaveTypeDTO) {
        LeaveType.LeaveName enumName;
        try {
            enumName = LeaveType.LeaveName.valueOf(leaveCategory.toUpperCase()); // String → Enum
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid leave category: " + leaveCategory);
        }

        LeaveType leaveType = leaveTypeRepository.findByName(enumName)
                .orElseThrow(() -> new RuntimeException("Leave not found with category: " + leaveCategory));

        if (leaveTypeDTO.getDescription() != null) {
            leaveType.setDescription(leaveTypeDTO.getDescription());
        }
        if (leaveTypeDTO.getMaxDays() != null) {
            leaveType.setMaxDays(leaveTypeDTO.getMaxDays());
        }

        LeaveType updated = leaveTypeRepository.save(leaveType);

        return new LeaveTypeDTO(
                updated.getLeaveId(),
                updated.getName().name(),   // Enum → String
                updated.getDescription(),
                updated.getMaxDays()
        );
    }
}

