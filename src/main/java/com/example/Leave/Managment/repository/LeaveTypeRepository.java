package com.example.Leave.Managment.repository;

import com.example.Leave.Managment.DTO.LeaveTypeDTO;
import com.example.Leave.Managment.entity.tables.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    Optional<LeaveType> findByName(LeaveType.LeaveName name);
}

