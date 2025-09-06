package com.example.Leave.Managment.repository;

import com.example.Leave.Managment.entity.tables.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    @Query("SELECT r, b FROM LeaveRequest r " +
            "LEFT JOIN LeaveBalance b " +
            "ON r.employee.id = b.employee.id " +
            "AND r.leaveType.leaveId = b.leaveType.leaveId " +
            "WHERE r.employee.id = :employeeId")
    List<Object[]> findRequestsWithBalance(Long employeeId);

    List<LeaveRequest> findByEmployee_Id(Long employeeId);
}
