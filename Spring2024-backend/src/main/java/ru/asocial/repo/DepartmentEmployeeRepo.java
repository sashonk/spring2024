package ru.asocial.repo;

import org.springframework.stereotype.Repository;

import ru.asocial.entity.DepartmentEmployeeEntity;

@Repository
public interface DepartmentEmployeeRepo extends org.springframework.data.repository.CrudRepository<DepartmentEmployeeEntity, String> {

}
