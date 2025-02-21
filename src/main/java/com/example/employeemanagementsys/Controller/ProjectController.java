package com.example.employeemanagementsys.Controller;

import com.example.employeemanagementsys.Dto.EmployeeProjectRequest;
import com.example.employeemanagementsys.Dto.ProjectRequest;
import com.example.employeemanagementsys.Dto.ProjectResponse;
import com.example.employeemanagementsys.Exceptions.ResourceNotFoundException;
import com.example.employeemanagementsys.Service.ProjectService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectResponse> addProject(@RequestBody ProjectRequest request) {
        ProjectResponse response = projectService.addProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/viewall")
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        List<ProjectResponse> projects = projectService.getAllProjects();
        return projects.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.ok().body(projects);
    }


    @GetMapping("/view/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok().body(projectService.getProjectById(id));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody ProjectRequest request) {
        return ResponseEntity.ok().body(projectService.updateProject(id, request));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{projectId}/employees/{employeeId}")
    public ResponseEntity<String> addEmployeeToProject(@PathVariable Long projectId,@PathVariable Long employeeId, @RequestBody EmployeeProjectRequest request)
    {
        projectService.addEmployeeToProject(projectId, employeeId, request.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body("Employee added to project successfully");
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }
}
