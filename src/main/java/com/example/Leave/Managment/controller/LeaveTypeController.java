package com.example.Leave.Managment.controller;


import com.example.Leave.Managment.DTO.LeaveTypeDTO;
import com.example.Leave.Managment.service.LeaveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class LeaveTypeController {
    @Autowired
    private LeaveTypeService leaveTypeService;

    @GetMapping("/getleavetype")
    public ResponseEntity<List<LeaveTypeDTO>> getLeaveType(){
        try{
            List<LeaveTypeDTO> leaveType = leaveTypeService.getLeaveType();
            if(leaveType.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(leaveType);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updateleavetype")
    public ResponseEntity<LeaveTypeDTO> UpdateLeaveType(@RequestParam String leaveCategory,
                                                        @RequestBody LeaveTypeDTO leaveTypeDTO) {
        try {
            LeaveTypeDTO updatedLeaveType = leaveTypeService.updateLeaveType(leaveCategory, leaveTypeDTO);
            return ResponseEntity.ok(updatedLeaveType);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
