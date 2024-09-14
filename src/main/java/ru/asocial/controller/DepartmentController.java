package ru.asocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.asocial.repo.DepartmentRepo;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentRepo departmentRepo;

    @GetMapping("/list")
    public List<String> listDepartments() {
        return departmentRepo.listDepartments();
    }


}
