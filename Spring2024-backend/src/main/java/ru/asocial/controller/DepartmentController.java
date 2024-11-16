package ru.asocial.controller;

import org.openapitools.api.DepartmentApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.asocial.entity.Department;
import ru.asocial.repo.DepartmentRepo;

@RestController
@RequestMapping("/api/department")
public class DepartmentController implements DepartmentApi {

    @Autowired
    private DepartmentRepo departmentRepo;

    @GetMapping("/list")
    public Iterable<Department> listDepartments() {
        return departmentRepo.findAll();
    }

}
