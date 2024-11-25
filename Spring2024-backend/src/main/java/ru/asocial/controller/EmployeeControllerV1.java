package ru.asocial.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.asocial.entity.EmployeeEntity;
import ru.asocial.entity.EmployeeGender;
import ru.asocial.repo.EmployeeRepo;

@RestController
@RequestMapping("/api/employee")
public class EmployeeControllerV1 {

	@Autowired
	private EmployeeRepo employeeRepo;

	@GetMapping("/{id}")
	public Map<String, Object> findById(@PathVariable Long id) {
		Optional<EmployeeEntity> optionalEmployee = employeeRepo.findById(id);

		if (optionalEmployee.isPresent()) {
			EmployeeEntity employee = optionalEmployee.get();
			Map<String, Object> result = new HashMap<>();
			result.put("id", employee.getId());
			result.put("first_name", employee.getFirstName());
			result.put("last_name", employee.getLastName());
			//result.put("gender", employee.get)
			return result;
		}
		return Collections.emptyMap();
	}

	@GetMapping("/search")
	public Iterable<EmployeeEntity> search(@RequestParam(required = false, name = "id") Long id,
			@RequestParam(name = "firstName", required = false) String firstName,
			@RequestParam(name = "lastName", required = false) String lastName,
			@RequestParam(name = "gender", required = false) String gender,
			@RequestParam(name = "birthDateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDateFrom,
			@RequestParam(name = "birthDateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDateTo) {

		EmployeeGender employeeGender = EmployeeGender.valueOf(gender);
		return employeeRepo.search(firstName, lastName, employeeGender.name(), java.sql.Date.valueOf(birthDateFrom), java.sql.Date.valueOf(birthDateTo));
	}
	

}
