package com.example.Leave.Managment.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveApprovalDTO {
    private Long approvalId;
    private Long employeeId;       // only ID
    private Long leaveTypeId;      // only ID
    private Date startDate;
    private Date endDate;
    private String status;         // PENDING / APPROVE / REJECT
    private Date appliedOn;
}
