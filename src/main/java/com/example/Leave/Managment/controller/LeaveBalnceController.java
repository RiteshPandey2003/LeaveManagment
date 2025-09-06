package com.example.Leave.Managment.controller;

import com.example.Leave.Managment.DTO.LeaveBalanceDTO;
import com.example.Leave.Managment.service.LeaveBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/balance")
public class LeaveBalnceController {

    @Autowired
    private LeaveBalanceService leaveBalanceService;

    @GetMapping("/employee/{id}")
    public ResponseEntity<List<LeaveBalanceDTO>> getLeaveBalnce(@PathVariable Long id){
           try{
              List<LeaveBalanceDTO> leaveBalance = leaveBalanceService.getLeaveBalance(id);
              if(leaveBalance.isEmpty()){
                  return ResponseEntity.noContent().build();
              }
              return ResponseEntity.ok(leaveBalance);
           }catch (Exception e){
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
           }
    }
}
