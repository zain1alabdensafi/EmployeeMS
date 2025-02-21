package com.example.employeemanagementsys.Dto;


import lombok.Data;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class EmployeeProjectRequest {
    @Getter
    @Setter
    @NotBlank(message = "Role cannot be blank")
    private String role;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;


}