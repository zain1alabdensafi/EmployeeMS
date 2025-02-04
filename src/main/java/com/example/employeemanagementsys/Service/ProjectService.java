package com.example.employeemanagementsys.Service;



import com.example.employeemanagementsys.Dto.EmployeeProjectRequest;
import com.example.employeemanagementsys.Dto.ProjectRequest;
import com.example.employeemanagementsys.Dto.ProjectResponse;
import com.example.employeemanagementsys.Repository.DepartmentRepository;
import com.example.employeemanagementsys.Repository.EmployeeProjectRepository;
import com.example.employeemanagementsys.Repository.EmployeeRepository;
import com.example.employeemanagementsys.Repository.ProjectRepository;
import com.example.employeemanagementsys.Tables.Department;
import com.example.employeemanagementsys.Tables.Employee;
import com.example.employeemanagementsys.Tables.EmployeeProject;
import com.example.employeemanagementsys.Tables.Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeProjectRepository employeeProjectRepository;

    public ProjectService(ProjectRepository projectRepository, DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, EmployeeProjectRepository employeeProjectRepository) {
        this.projectRepository = projectRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.employeeProjectRepository = employeeProjectRepository;
    }


    public ProjectResponse addProject(ProjectRequest request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .department(department)
                .build();

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
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return mapToResponse(project);
    }


    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setDepartment(department);

        project = projectRepository.save(project);
        return mapToResponse(project);
    }


    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(id);
    }


    private ProjectResponse mapToResponse(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setName(project.getName());
        response.setDescription(project.getDescription());
        response.setDepartmentName(project.getDepartment().getName());
        return response;
    }

    public void addEmployeeToProject(Long projectId, Long employeeId, EmployeeProjectRequest request) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));


        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));


        EmployeeProject employeeProject = new EmployeeProject();
        employeeProject.setEmployee(employee);
        employeeProject.setProject(project);
        employeeProject.setRole(request.getRole());
        employeeProject.setStartDate(request.getStartDate());

        employeeProjectRepository.save(employeeProject);
    }

}

