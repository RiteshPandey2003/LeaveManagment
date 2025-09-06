package com.example.Leave.Managment.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestDTO {
    private Long requestId;
    private EmployeeDTO employee;
    private LeaveTypeDTO leaveType;
    private Date startDate;
    private Date endDate;
    private String status;         // PENDING / APPROVE / REJECT
    private Date appliedOn;
}
