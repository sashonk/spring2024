package ru.asocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.asocial.entity.Employee;
import ru.asocial.repo.EmployeeRepo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
}
