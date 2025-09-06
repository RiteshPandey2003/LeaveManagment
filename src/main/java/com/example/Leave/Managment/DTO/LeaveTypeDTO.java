package com.example.Leave.Managment.DTO;

import com.example.Leave.Managment.entity.tables.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveTypeDTO {
    private Long leaveId;
    private String name;
    private String description;
    private Integer maxDays;

    public LeaveTypeDTO(Long leaveId, LeaveType.LeaveName name, String description, Integer maxDays) {
        this.leaveId = leaveId;
        this.name = name.name();
        this.description = description;
        this.maxDays = maxDays;
    }

}
