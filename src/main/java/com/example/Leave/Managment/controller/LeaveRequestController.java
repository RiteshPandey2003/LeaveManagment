package com.example.Leave.Managment.controller;

import com.example.Leave.Managment.DTO.LeaveRequestDTO;
import com.example.Leave.Managment.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/leaveRequest")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping("/getLeaveRequest/{id}")
    public ResponseEntity<List<LeaveRequestDTO>> GetAllRequest(@PathVariable Long id){
        try{
            List<LeaveRequestDTO> leaveRequests = leaveRequestService.getAllRequests(id);
            if(leaveRequests.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(leaveRequests);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PostMapping("/apply")
    public ResponseEntity<String> applyLeave(@RequestParam Long employeeId,
                                             @RequestParam Long leaveTypeId,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        String response = leaveRequestService.applyLeave(employeeId, leaveTypeId, startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{requestId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long requestId,
            @RequestParam String status,
            @RequestParam String role) {

        String response = leaveRequestService.updateLeaveStatus(requestId, status, role);
        return ResponseEntity.ok(response);
    }
}
