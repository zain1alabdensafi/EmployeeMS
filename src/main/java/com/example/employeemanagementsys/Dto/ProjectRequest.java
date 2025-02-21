package com.example.employeemanagementsys.Dto;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProjectRequest {
    @NotBlank(message = "Project name cannot be blank")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Department ID is required")
    private Long departmentId;
}

