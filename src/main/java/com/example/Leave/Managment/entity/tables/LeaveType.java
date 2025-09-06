package com.example.Leave.Managment.entity.tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveId;

    @Enumerated(EnumType.STRING)
    private LeaveName name;
    private String description;
    private Integer maxDays;

    public LeaveType(LeaveName name, String description){
        this.name = name;
        this.description = description;
        this.maxDays = name.getMaxDays();

    }

    @Getter
    public enum LeaveName{
        CASULA(7),
        SICK(10),
        EARNED(15),
        FLEXY(14);

        private final int maxDays;

        LeaveName(int maxDays){
            this.maxDays = maxDays;
        }

    }
}




