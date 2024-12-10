package ru.asocial.controller.v2;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.openapitools.api.ApiUtil;
import org.openapitools.api.EmployeeApi;
import org.openapitools.model.CreateEmployee;
import org.openapitools.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ru.asocial.entity.DepartmentEmployeeEntity;
import ru.asocial.entity.DepartmentEntity;
import ru.asocial.entity.EmployeeEntity;
import ru.asocial.entity.EmployeeGender;
import ru.asocial.mapper.EmployeeMapper;
import ru.asocial.repo.DepartmentEmployeeRepo;
import ru.asocial.repo.DepartmentRepo;
import ru.asocial.repo.EmployeeRepo;
import ru.asocial.utils.NullHelper;

@RestController
@RequestMapping("/api/v2/employee")
public class EmployeeController implements EmployeeApi {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private DepartmentEmployeeRepo departmentEmployeeRepo;

    /**
     * GET /employee/search : Search employees
     *
     * @param limit         (required)
     * @param id            (optional)
     * @param firstName     (optional)
     * @param lastName      (optional)
     * @param birthDateFrom (optional)
     * @param birthDateTo   (optional)
     * @param gender        (optional)
     * @param departmentId  (optional)
     * @return List of employees (status code 200)
     */
    @Operation(operationId = "search", summary = "Search employees", responses = {
            @ApiResponse(responseCode = "200", description = "List of employees", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Employee.class))) }) })
    @RequestMapping(method = RequestMethod.GET, value = "/search", produces = { "application/json" })
    @Override
    public ResponseEntity<List<Employee>> search(
            @Parameter(name = "id", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "id", required = false) Long id,
            @Parameter(name = "firstName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "firstName", required = false) String firstName,
            @Parameter(name = "lastName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "lastName", required = false) String lastName,
            @Parameter(name = "birthDateFrom", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "birthDateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDateFrom,
            @Parameter(name = "birthDateTo", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "birthDateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDateTo,
            @Parameter(name = "gender", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "gender", required = false) String gender) {

        Iterable<EmployeeEntity> data = employeeRepo.search(firstName, lastName, gender,
                NullHelper.nullSafeToDate(birthDateFrom), NullHelper.nullSafeToDate(birthDateTo), id);
        EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);
        List<Employee> result = new LinkedList<>();
        for (EmployeeEntity entity : data) {
            Employee employee = mapper.entityToDto(entity);
            result.add(employee);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * GET /employee/searchInDepartments : Search employees
     *
     * @param limit         (required)
     * @param departmentIds (required)
     * @param id            (optional)
     * @param firstName     (optional)
     * @param lastName      (optional)
     * @param birthDateFrom (optional)
     * @param birthDateTo   (optional)
     * @param gender        (optional)
     * @return List of employees (status code 200)
     */
    @Operation(operationId = "searchInDepartments", summary = "Search employees", responses = {
            @ApiResponse(responseCode = "200", description = "List of employees", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Employee.class))) }) })
    @RequestMapping(method = RequestMethod.GET, value = "/searchInDepartments", produces = { "application/json" })
    @Override
    public ResponseEntity<List<Employee>> searchInDepartments(
            @NotNull @Parameter(name = "departmentIds", description = "", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "departmentIds", required = true) List<String> departmentIds,
            @NotNull @Parameter(name = "page_number", description = "", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "page_number", required = true) Integer pageNumber,
            @NotNull @Parameter(name = "page_size", description = "", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "page_size", required = true) Integer pageSize,
            @Parameter(name = "id", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "id", required = false) Long id,
            @Parameter(name = "firstName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "firstName", required = false) String firstName,
            @Parameter(name = "lastName", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "lastName", required = false) String lastName,
            @Parameter(name = "birthDateFrom", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "birthDateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDateFrom,
            @Parameter(name = "birthDateTo", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "birthDateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDateTo,
            @Parameter(name = "gender", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "gender", required = false) String gender,
            @Parameter(name = "sort_field", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "sort_field", required = false) String sortField,
            @Parameter(name = "sort_order", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "sort_order", required = false) String sortOrder
        ) {
        if (pageSize < 0 || pageNumber < 0) {
            return ResponseEntity.unprocessableEntity().body(Collections.emptyList());
        }
        
        PageRequest pr;
        if (sortField == null) {
            pr = PageRequest.of(pageNumber, pageSize);
        }
        else {
            pr = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.valueOf(sortOrder), sortField));
        }

        Page<EmployeeEntity> data = employeeRepo.searchEmployeesCurrentlyWorkInDepartments(id, firstName, lastName,
                gender, NullHelper.nullSafeToDate(birthDateFrom), NullHelper.nullSafeToDate(birthDateTo), departmentIds, pr);
        EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);
        List<Employee> result = new LinkedList<>();
        for (EmployeeEntity entity : data) {
            Employee employee = mapper.entityToDto(entity);
            result.add(employee);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(method = RequestMethod.POST, value = "", consumes = { "application/json" })
    @Transactional
    @Override
    public ResponseEntity<String> create(
            @Parameter(name = "Employee", description = "", required = true) @Valid @RequestBody CreateEmployee createDTO) {

        if (createDTO.getDepartmentId() == null || createDTO.getBirthDate() == null || createDTO.getDateFrom() == null
                || createDTO.getHireDate() == null) {
            return ResponseEntity.badRequest().body("not all required fields provided");
        }

        Optional<DepartmentEntity> departmentEntityOpt = departmentRepo.findById(createDTO.getDepartmentId());
        if (departmentEntityOpt.isEmpty()) {
            return ResponseEntity.unprocessableEntity().body("department not found: " + createDTO.getDepartmentId());
        }

        DepartmentEntity departmentEntity = departmentEntityOpt.get();
        EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);
        EmployeeEntity entity = mapper.dtoToEntity(createDTO);
        entity.setSource("api");
        if (entity.getBirthDate() == null || entity.getHireDate() == null || entity.getFirstName() == null
                || entity.getLastName() == null) {
            return ResponseEntity.unprocessableEntity()
                    .body("can't save employee: not all required attributes provided");
        }

        EmployeeEntity newEntity = employeeRepo.save(entity);

        DepartmentEmployeeEntity departmentEmployee = new DepartmentEmployeeEntity();
        departmentEmployee.setDepartmentId(departmentEntity.getId());
        departmentEmployee.setEmployeeId(newEntity.getId());
        departmentEmployee.setFromDate(createDTO.getDateFrom());
        departmentEmployee.setToDate(createDTO.getDateTo());

        departmentEmployeeRepo.save(departmentEmployee);

        // departmentEntity.addEmployee(newEntity);
        // departmentRepo.save(departmentEntity);
        // DepartmentEmployee binding = new DepartmentEmployee();
        // binding.setEmployeeId(newEntity.getId());
        // binding.setDepartmentId(departmentEntity.getId());
        // binding.setFromDate(createDTO.getDateFrom());
        // binding.setToDate(createDTO.getDateTo());
        // departmentEmployeeRepo.save(binding);

        return ResponseEntity.created(URI.create(getServerBaseUrl() + "/api/v2/employee/" + entity.getId())).build();
    }

    @Override
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
