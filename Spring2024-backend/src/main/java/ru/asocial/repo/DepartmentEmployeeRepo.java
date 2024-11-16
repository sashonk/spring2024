package ru.asocial.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.asocial.entity.DepartmentEmployee;


public interface DepartmentEmployeeRepo extends CrudRepository<DepartmentEmployee, Long>{

}
