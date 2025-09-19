package com.example.Leave.Managment.service;

import com.example.Leave.Managment.DTO.AllLeaveByEmployeeDTO;
import com.example.Leave.Managment.DTO.EmployeeDTO;
import com.example.Leave.Managment.entity.tables.Employee;
import com.example.Leave.Managment.entity.tables.LeaveBalance;
import com.example.Leave.Managment.entity.tables.LeaveRequest;
import com.example.Leave.Managment.repository.EmployeeRepository;
import com.example.Leave.Managment.utility.EmployeeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository empRepo;

   //put employees
   @CachePut(value = "employees", key = "#result.id")
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

//    @Cacheable(value = "employees") dont use cache for that much data
    public List<EmployeeDTO> getAllEmployees(int pageNo, int pageSize, Sort sort,
                                             Long id, String name, String email,
                                             String department, String role, Date joinDate) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Specification<Employee> spec = Specification.allOf(
                EmployeeSpecification.hasId(id),
                EmployeeSpecification.hasName(name),
                EmployeeSpecification.hasEmail(email),
                EmployeeSpecification.hasDepartment(department),
                EmployeeSpecification.hasRole(role),
                EmployeeSpecification.hasJoinDate(joinDate)
        );


        return empRepo.findAll(spec, pageable)
                .stream()
                .map(emp -> new EmployeeDTO(
                        emp.getId(),
                        emp.getName(),
                        emp.getEmail(),
                        emp.getDepartment(),
                        emp.getRole(),
                        emp.getJoinDate()
                ))
                .collect(Collectors.toList());
    }

    @Cacheable(value = "employees", key = "#id")
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


    @CachePut(value = "employees", key = "#dto.id")
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

    public List<AllLeaveByEmployeeDTO> getEmployeeLeaveByName(Long id) {
        List<Object[]> result = empRepo.findRequestsWithBalances(id);

        List<AllLeaveByEmployeeDTO> dtoList = new ArrayList<>();

        for (Object[] row : result) {
            LeaveRequest request = (LeaveRequest) row[0];
            LeaveBalance balance = (LeaveBalance) row[1];

            AllLeaveByEmployeeDTO dto = new AllLeaveByEmployeeDTO();
            dto.setName(request.getEmployee().getName());

            HashMap<String, Integer> map = new HashMap<>();
            map.put(String.valueOf(request.getLeaveType().getName()),
                    balance != null ? balance.getremainingDays() : 0);

            dto.setMap(map);
            dtoList.add(dto);
        }

        return dtoList;
    }
}
