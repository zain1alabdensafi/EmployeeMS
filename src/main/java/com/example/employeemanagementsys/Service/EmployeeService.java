package com.example.employeemanagementsys.Service;

import com.example.employeemanagementsys.Dto.EmployeeRequest;
import com.example.employeemanagementsys.Dto.EmployeeResponse;
import com.example.employeemanagementsys.Exceptions.ResourceNotFoundException;
import com.example.employeemanagementsys.Repository.DepartmentRepository;
import com.example.employeemanagementsys.Repository.EmployeeRepository;
import com.example.employeemanagementsys.Tables.Department;
import com.example.employeemanagementsys.Tables.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found");
        }
        return employees.stream().map(this::mapToEmployeeResponse).collect(Collectors.toList());
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + id + " not found"));
        return mapToEmployeeResponse(employee);
    }

    public EmployeeResponse createEmployee(EmployeeRequest request) {
        if (request.getName() == null || request.getEmail() == null || request.getPhone() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request: Name, Email, and Phone are required");
        }

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setSalary(request.getSalary());

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department with ID " + request.getDepartmentId() + " not found"));
            employee.setDepartment(department);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToEmployeeResponse(savedEmployee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee with ID " + id + " not found");
        }
        employeeRepository.deleteById(id);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + id + " not found"));

        if (request.getName() != null) {
            employee.setName(request.getName());
        }
        if (request.getEmail() != null) {
            employee.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            employee.setPhone(request.getPhone());
        }
        if (request.getSalary() != null) {
            employee.setSalary(request.getSalary());
        }
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department with ID " + request.getDepartmentId() + " not found"));
            employee.setDepartment(department);
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToEmployeeResponse(updatedEmployee);
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setEmail(employee.getEmail());
        response.setPhone(employee.getPhone());
        response.setSalary(employee.getSalary());

        if (employee.getDepartment() != null) {
            response.setDepartmentName(employee.getDepartment().getName());
        } else {
            response.setDepartmentName("No department assigned");
        }

        return response;
    }
}
