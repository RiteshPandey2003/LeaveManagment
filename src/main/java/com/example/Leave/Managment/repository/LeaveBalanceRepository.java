package com.example.Leave.Managment.repository;

import com.example.Leave.Managment.entity.tables.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
    List<LeaveBalance> findBalanceByEmployeeId(Long employeeId);

    @Query("SELECT b FROM LeaveBalance b " +
            "WHERE b.employee.id = :employeeId " +
            "AND b.leaveType.leaveId = :leaveTypeId")
    LeaveBalance findByEmployeeAndLeaveType(Long employeeId, Long leaveTypeId);
}
