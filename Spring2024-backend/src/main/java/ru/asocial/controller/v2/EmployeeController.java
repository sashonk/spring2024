package ru.asocial.controller.v2;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.openapitools.api.ApiUtil;
import org.openapitools.api.EmployeeApi;
import org.openapitools.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import ru.asocial.entity.EmployeeEntity;
import ru.asocial.entity.EmployeeGender;
import ru.asocial.mapper.EmployeeMapper;
import ru.asocial.repo.EmployeeRepo;

@RestController
@RequestMapping("/api/v2/employee")
public class EmployeeController implements EmployeeApi {

    @Autowired
    private EmployeeRepo employeeRepo;

    @GetMapping("/search")
    public Iterable<EmployeeEntity> search(@RequestParam(required = false, name = "id") Long id,
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName,
            @RequestParam(name = "gender", required = false) String gender,
            @RequestParam(name = "birthDateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDateFrom,
            @RequestParam(name = "birthDateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDateTo) {

        EmployeeGender employeeGender = EmployeeGender.valueOf(gender);
        return employeeRepo.search(firstName, lastName, employeeGender.name(), java.sql.Date.valueOf(birthDateFrom),
                java.sql.Date.valueOf(birthDateTo));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/", consumes = { "application/json" })
    public ResponseEntity<Void> employeePost(
            @Parameter(name = "Employee", description = "", required = true) @Valid @RequestBody Employee employee) {
        
        EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);
        EmployeeEntity entity = mapper.dtoToEntity(employee);
        EmployeeEntity newEntity = employeeRepo.save(entity);
        return ResponseEntity.created(URI.create(getServerBaseUrl() + "/api/v2/employee/" + newEntity.getId())).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{employeeId}", produces = { "application/json" })
    public ResponseEntity<Employee> getById(
            @Parameter(name = "employeeId", description = "The ID of the employee to retrieve", required = true, in = ParameterIn.PATH) @PathVariable("employeeId") Integer employeeId) {

        Optional<EmployeeEntity> employeeOpt = employeeRepo.findById(Long.valueOf(employeeId));
        if (employeeOpt.isPresent()) {
            EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);
            EmployeeEntity entity = employeeOpt.get();
            Employee employee = employeeMapper.entityToDto(entity);
            return ResponseEntity.ok(employee);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    private String getServerBaseUrl() {
        return "http://localhost:9090";
    }
}
