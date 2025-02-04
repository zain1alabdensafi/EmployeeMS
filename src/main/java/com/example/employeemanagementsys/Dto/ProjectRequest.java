package com.example.employeemanagementsys.Dto;


import lombok.Data;

@Data
public class ProjectRequest {
    private String name;
    private String description;
    private Long departmentId;
}

