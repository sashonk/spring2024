package ru.asocial.controller.v2;

import java.util.LinkedList;
import java.util.List;

import org.openapitools.api.DepartmentApi;
import org.openapitools.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.asocial.repo.DepartmentRepo;

@RestController(value = "api/v2/department")
public class DepartmentController implements DepartmentApi{
	
    @Autowired
    private DepartmentRepo departmentRepo;

	@RequestMapping(value = "/list")
	public ResponseEntity<List<Department>> getDepartments() {
		Iterable<ru.asocial.entity.Department> data = departmentRepo.findAll();
		List<Department> result = new LinkedList<>();
		for (ru.asocial.entity.Department entity : data) {
			Department dto = new Department();
			dto.setId(entity.getId());
			dto.setName(entity.getDeptName());
		}
		
		return ResponseEntity.ok(result);
	}
}
