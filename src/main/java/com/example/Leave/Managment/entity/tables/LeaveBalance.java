package com.example.Leave.Managment.entity.tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long balanceId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "leave_type_id", nullable = false)
    private LeaveType leaveType;

    @Column(nullable = false)
    private Integer usedLeave = 0;

    public Integer getremainingDays(){
        return leaveType.getMaxDays() - usedLeave;
    }

    public boolean useLeave(int days){
        if(getremainingDays() >= days){
            usedLeave += days;
            return true;
        }
        return false;
    }
}
