package ru.asocial.repo;

import ru.asocial.entity.Employee;

public interface EmployeeRepo extends org.springframework.data.repository.CrudRepository<Employee, Long> {


}
