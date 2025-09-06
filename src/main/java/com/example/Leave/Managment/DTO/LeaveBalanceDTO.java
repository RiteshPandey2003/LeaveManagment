package com.example.Leave.Managment.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveBalanceDTO {
    private Long balanceId;
    private EmployeeDTO employeeId;   // only ID
    private LeaveTypeDTO leaveType;
    private Integer remainingDays;
}
