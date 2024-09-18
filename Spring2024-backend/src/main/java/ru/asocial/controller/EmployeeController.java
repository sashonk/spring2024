package ru.asocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.asocial.entity.Employee;
import ru.asocial.repo.EmployeeRepo;

import java.util.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepo employeeRepo;

    @GetMapping("/{id}")
    public Map<String, Object> findById(@PathVariable Long id) {
        Optional<Employee> optionalEmployee = employeeRepo.findById(id);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            Map<String, Object> result = new HashMap<>();
            result.put("id", employee.getId());
            result.put("first_name", employee.getFirstName());
            result.put("last_name", employee.getLastName());
            return result;
        }
        return Collections.emptyMap();
    }

    @GetMapping("/find")
    public Iterable<Employee> findAll() {
        Iterable<Employee> iter = employeeRepo.findAll();
        return iter;
    }

    @GetMapping("/findBy")
    public Iterable<Employee> findByLastNameContaining(@RequestParam(name = "str") String str) {
        if (str == null || str.isEmpty()) {
            return Collections.emptyList();
        }
        return employeeRepo.findByLastNameContaining(str);
    }
}
