package com.example.employeemanagementsys.Dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeProjectRequest {
    private String role;
    private LocalDateTime startDate;
}

