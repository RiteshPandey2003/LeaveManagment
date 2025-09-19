package com.example.Leave.Managment.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllLeaveByEmployeeDTO {
   private String Name;
   private Map<String , Integer> map;
}
