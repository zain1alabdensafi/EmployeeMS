package com.example.employeemanagementsys.Dto;


import lombok.Data;

import java.util.List;

@Data
public class DepartmentResponse {
    private Long id;
    private String name;
    private List<String> employeeNames;
}
/*

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public List<String> getEmployeeNames() {
    return employeeNames;
}

public void setEmployeeNames(List<String> employeeNames) {
    this.employeeNames = employeeNames;
}
*/