package com.example.Leave.Managment.service;

import com.example.Leave.Managment.DTO.EmployeeDTO;
import com.example.Leave.Managment.entity.tables.Employee;
import com.example.Leave.Managment.repository.EmployeeRepository;
import com.example.Leave.Managment.utility.EmployeeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository empRepo;

   //put employees
    public EmployeeDTO postEmployee(EmployeeDTO empDto) {
        // DTO → Entity
        Employee employee = new Employee();
        employee.setName(empDto.getName());
        employee.setEmail(empDto.getEmail());
        employee.setDepartment(empDto.getDepartment());
        employee.setRole(empDto.getRole());
        employee.setJoinDate(empDto.getJoinDate());

        // Save Entity
        Employee saved = empRepo.save(employee);

        // Entity → DTO
        return new EmployeeDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getDepartment(),
                saved.getRole(),
                saved.getJoinDate()
        );
    }

    //get all employees
    public List<EmployeeDTO> getAllEmployees(int pageNo, int pageSize, Sort sort,Long id, String name, String email, String department, String role, Date joinDate) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Specification<Employee> spec = Specification.allOf(
                EmployeeSpecification.hasId(id),
                EmployeeSpecification.hasName(name),
                EmployeeSpecification.hasDepartment(department),
                EmployeeSpecification.hasEmail(email),
                EmployeeSpecification.hasRole(role),
                EmployeeSpecification.hasJoinDate(joinDate));

        return empRepo.findAll(spec,pageable).stream().map(emp -> new EmployeeDTO(
                emp.getId(),
                emp.getName(),
                emp.getEmail(),
                emp.getDepartment(),
                emp.getRole(),
                emp.getJoinDate()
        )).collect(Collectors.toList());
    }


    public EmployeeDTO singleEmployee(Long id) {
        return  empRepo.findById(id).map(emp -> new EmployeeDTO(
                emp.getId(),
                emp.getName(),
                emp.getEmail(),
                emp.getDepartment(),
                emp.getRole(),
                emp.getJoinDate()
        )).orElse(null);
    }


    //update
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO empdto) {
        return empRepo.findById(id).map(emp -> {
            emp.setName(empdto.getName());
            emp.setEmail(empdto.getEmail());
            emp.setRole(empdto.getRole());
            emp.setDepartment(empdto.getDepartment());
            emp.setJoinDate(empdto.getJoinDate());
            Employee saved = empRepo.save(emp);

            return new EmployeeDTO(
                    saved.getId(),
                    saved.getName(),
                    saved.getEmail(),
                    saved.getRole(),
                    saved.getDepartment(),
                    saved.getJoinDate()
            );
        }).orElse(null);
    }
}
