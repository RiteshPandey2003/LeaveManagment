package com.example.Leave.Managment.repository;

import com.example.Leave.Managment.DTO.AllLeaveByEmployeeDTO;
import com.example.Leave.Managment.entity.tables.Employee;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    @Query("SELECT r, b " +
            "FROM LeaveRequest r " +
            "LEFT JOIN LeaveBalance b " +
            "WITH r.employee = b.employee AND r.leaveType = b.leaveType " +
            "WHERE r.employee.id = :employeeId")
    List<Object[]> findRequestsWithBalances(@Param("employeeId") Long employeeId);

}
