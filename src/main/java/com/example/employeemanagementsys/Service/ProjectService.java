package com.example.employeemanagementsys.Service;

import com.example.employeemanagementsys.Dto.ProjectRequest;
import com.example.employeemanagementsys.Dto.ProjectResponse;
import com.example.employeemanagementsys.Exceptions.ResourceNotFoundException;
import com.example.employeemanagementsys.Repository.DepartmentRepository;
import com.example.employeemanagementsys.Repository.EmployeeProjectRepository;
import com.example.employeemanagementsys.Repository.EmployeeRepository;
import com.example.employeemanagementsys.Repository.ProjectRepository;
import com.example.employeemanagementsys.Service.Validation.ObjectValidator;
import com.example.employeemanagementsys.Tables.Department;
import com.example.employeemanagementsys.Tables.Employee;
import com.example.employeemanagementsys.Tables.Project;
import com.example.employeemanagementsys.Tables.EmployeeProject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeProjectRepository employeeProjectRepository;
    private final ObjectValidator<ProjectRequest> objectValidator;

    public ProjectService(ProjectRepository projectRepository,
                          DepartmentRepository departmentRepository,
                          EmployeeRepository employeeRepository,
                          EmployeeProjectRepository employeeProjectRepository,
                          ObjectValidator<ProjectRequest> objectValidator) {
        this.projectRepository = projectRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.employeeProjectRepository = employeeProjectRepository;
        this.objectValidator = objectValidator;
    }

    public ProjectResponse addProject(ProjectRequest request) {
        objectValidator.validate(request);

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setDepartment(department);

        project = projectRepository.save(project);
        return mapToResponse(project);
    }

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return mapToResponse(project);
    }

    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        objectValidator.validate(request);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setDepartment(department);

        project = projectRepository.save(project);
        return mapToResponse(project);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found");
        }
        projectRepository.deleteById(id);
    }


    public void addEmployeeToProject(Long projectId, Long employeeId, String role) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project with ID " + projectId + " not found"));


        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + employeeId + " not found"));

        EmployeeProject employeeProject = new EmployeeProject();
        employeeProject.setEmployee(employee);
        employeeProject.setProject(project);
        employeeProject.setRole(role);
        employeeProject.setStartDate(LocalDateTime.now());


        employeeProjectRepository.save(employeeProject);
    }


    private ProjectResponse mapToResponse(Project project) {
        return new ProjectResponse(project.getId(), project.getName(), project.getDescription(), project.getDepartment().getName());
    }
}
