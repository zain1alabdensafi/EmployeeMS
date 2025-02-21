package com.example.employeemanagementsys.Service;

import com.example.employeemanagementsys.Tables.Employee;
import com.example.employeemanagementsys.Dto.DepartmentRequest;
import com.example.employeemanagementsys.Dto.DepartmentResponse;
import com.example.employeemanagementsys.Exceptions.ResourceNotFoundException;
import com.example.employeemanagementsys.Repository.DepartmentRepository;
import com.example.employeemanagementsys.Tables.Department;
import com.example.employeemanagementsys.Service.Validation.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ObjectValidator<DepartmentRequest> objectValidator; // Inject the ObjectValidator

    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(this::mapToDepartmentResponse).collect(Collectors.toList());
    }

    public DepartmentResponse getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department with ID " + id + " not found"));
        return mapToDepartmentResponse(department);
    }

    public DepartmentResponse createDepartment(DepartmentRequest request) {
        objectValidator.validate(request); // Validate before creating the department

        Department department = new Department();
        department.setName(request.getName());
        Department savedDepartment = departmentRepository.save(department);
        return mapToDepartmentResponse(savedDepartment);
    }

    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {
        objectValidator.validate(request); // Validate before updating the department

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department with ID " + id + " not found"));
        department.setName(request.getName());
        Department updatedDepartment = departmentRepository.save(department);
        return mapToDepartmentResponse(updatedDepartment);
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department with ID " + id + " not found"));
        departmentRepository.delete(department);
    }

    private DepartmentResponse mapToDepartmentResponse(Department department) {
        DepartmentResponse response = new DepartmentResponse();
        response.setId(department.getId());
        response.setName(department.getName());

        List<String> employeeNames = department.getEmployees() != null ?
                department.getEmployees().stream().map(Employee::getName).collect(Collectors.toList()) : List.of();

        response.setEmployeeNames(employeeNames);
        return response;
    }
}
