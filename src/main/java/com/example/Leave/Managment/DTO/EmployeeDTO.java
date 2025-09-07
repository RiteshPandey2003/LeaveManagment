package com.example.Leave.Managment.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO  implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String email;
    private String department;
    private String role;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date joinDate;
}
