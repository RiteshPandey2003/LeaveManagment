package com.example.Leave.Managment.utility;


import com.example.Leave.Managment.entity.tables.LeaveRequest;
import com.example.Leave.Managment.entity.tables.LeaveType;
import org.springframework.data.jpa.domain.Specification;

public class LeaveRequestSpecification {
    public static Specification<LeaveRequest> hasEmployeeName(String employeeName) {
//        System.out.println("hitting nmae specifiaction");
        return (root, query, cb) ->
                employeeName == null ? null :
                        cb.like(cb.lower(root.get("employee").get("name")), "%" + employeeName.toLowerCase() + "%");
    }

    public static Specification<LeaveRequest> hasLeaveTypeName(String leaveTypeName) {
        return (root, query, cb) ->
                leaveTypeName == null ? null :
                        cb.equal(root.get("leaveType").get("name"), LeaveType.LeaveName.valueOf(leaveTypeName.toUpperCase()));
    }
}
