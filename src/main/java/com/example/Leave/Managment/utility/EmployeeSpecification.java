package com.example.Leave.Managment.utility;

import com.example.Leave.Managment.entity.tables.Employee;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class EmployeeSpecification {

    public static Specification<Employee> hasName(String name){
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Employee> hasEmail(String email){
        return (root, query, cb) ->
                email == null ? null : cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() +"%");
    }

    public static Specification<Employee> hasDepartment(String department){
        return (root, query, cb) ->
                department == null ? null : cb.equal(root.get("department"), department);
    }

    public static Specification<Employee> hasRole(String role){
        return (root, query, cb) ->
                role == null ? null : cb.equal(root.get("role"),  role);
    }

    public static Specification<Employee> hasJoinDate(Date joinDate){
        return (root, query, cb) ->
                joinDate == null ? null : cb.equal(root.get("joinDate"), joinDate);
    }

    public static Specification<Employee> hasId(Long Id){
        return (root, query, cb) ->
                Id == null ? null : cb.equal(root.get("id"), Id);
    }
}
