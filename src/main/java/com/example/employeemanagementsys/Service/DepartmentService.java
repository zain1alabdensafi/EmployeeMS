package com.example.employeemanagementsys.Service;


import com.example.employeemanagementsys.Controller.DepartmentRequest;
import com.example.employeemanagementsys.Dto.DepartmentResponse;
import com.example.employeemanagementsys.Repository.DepartmentRepository;
import com.example.employeemanagementsys.Tables.Department;
import com.example.employeemanagementsys.Tables.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(this::mapToDepartmentResponse).collect(Collectors.toList());
    }

    public DepartmentResponse getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
        return mapToDepartmentResponse(department);
    }


    public DepartmentResponse createDepartment(DepartmentRequest request) {
        Department department = new Department();
        department.setName(request.getName());
        Department savedDepartment = departmentRepository.save(department);
        return mapToDepartmentResponse(savedDepartment);
    }

    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
        department.setName(request.getName());
        Department updatedDepartment = departmentRepository.save(department);
        return mapToDepartmentResponse(updatedDepartment);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }


    private DepartmentResponse mapToDepartmentResponse(Department department) {
        DepartmentResponse response = new DepartmentResponse();
        response.setId(department.getId());
        response.setName(department.getName());
        response.setEmployeeNames(department.getEmployees().stream().map(Employee::getName).collect(Collectors.toList()));
        return response;
    }
}
