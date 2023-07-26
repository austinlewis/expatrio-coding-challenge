package com.expatrio.assignment.controllers;

import com.expatrio.assignment.models.Department;
import com.expatrio.assignment.payload.request.AddDepartmentRequest;
import com.expatrio.assignment.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping()
    public String allAccess() {
        return "Public Content.";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Department> addDepartment(@Valid @RequestBody AddDepartmentRequest departmentRequest) {
        Department department = departmentService.addDepartment(new Department(null, departmentRequest.getName()));
        return ResponseEntity.ok(department);
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
