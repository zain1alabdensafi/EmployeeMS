package com.example.employeemanagementsys.Controller;



import com.example.employeemanagementsys.Dto.EmployeeProjectRequest;
import com.example.employeemanagementsys.Dto.ProjectRequest;
import com.example.employeemanagementsys.Dto.ProjectResponse;
import com.example.employeemanagementsys.Service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping("/create")
    public ResponseEntity<ProjectResponse> addProject(@RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.addProject(request));
    }


    @GetMapping("/viewall")
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }


    @GetMapping("/view/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok("Project deleted successfully");
    }

    @PostMapping("/{projectId}/employees/{employeeId}")
    public ResponseEntity<String> addEmployeeToProject(
            @PathVariable Long projectId,
            @PathVariable Long employeeId,
            @RequestBody EmployeeProjectRequest request) {
        projectService.addEmployeeToProject(projectId, employeeId, request);
        return ResponseEntity.ok("Employee added to project successfully");
    }
}

