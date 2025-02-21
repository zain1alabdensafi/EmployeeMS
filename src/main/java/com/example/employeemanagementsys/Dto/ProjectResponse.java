package com.example.employeemanagementsys.Dto;

import lombok.Data;

@Data
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private String departmentName;

    public ProjectResponse(Long id, String name, String description, String name1) {
    }
}

