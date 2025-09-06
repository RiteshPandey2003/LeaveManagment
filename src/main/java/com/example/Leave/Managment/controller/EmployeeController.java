package com.example.Leave.Managment.controller;

import com.example.Leave.Managment.DTO.EmployeeDTO;
import com.example.Leave.Managment.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/employees") // base URL
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> postEmployee(@RequestBody EmployeeDTO emp) {
        try {
            EmployeeDTO savedEmployee = employeeService.postEmployee(emp);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedEmployee);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping("/getemployee")
    public ResponseEntity<List<EmployeeDTO>> GetEmployee(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Date joinDate){
        try{
            Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            List<EmployeeDTO> employees = employeeService.getAllEmployees(pageNo, pageSize, sort, id, name, email, role, department, joinDate);
            if(employees.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(employees);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getemployee/{id}")
    public ResponseEntity<EmployeeDTO> singleEmployee(@PathVariable Long id){
         try{
             EmployeeDTO emp = employeeService.singleEmployee(id);
             if(emp == null){
                 return ResponseEntity.noContent().build();
             }
             return ResponseEntity.ok(emp);
         }catch(Exception e){
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updateemployee/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO emp){
        try{
            EmployeeDTO updatedEmployee = employeeService.updateEmployee(id,emp);
            if(updatedEmployee == null){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(emp);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
