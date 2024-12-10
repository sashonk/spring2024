package ru.asocial.repo;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.asocial.entity.EmployeeEntity;

@Repository
public interface EmployeeRepo extends org.springframework.data.repository.CrudRepository<EmployeeEntity, Long> {

    Iterable<EmployeeEntity> findByLastNameContaining(String str);

    @Query(nativeQuery = true, value = "SELECT * FROM employee \n"
            + "WHERE (:birthDateFrom IS NULL OR birth_date >= :birthDateFrom) \n"
            + "  AND (:birthDateTo IS NULL OR birth_date <= :birthDateTo) \n"
            + "  AND (:firstNamePattern IS NULL OR first_name = :firstNamePattern) \n"
            + "  AND (:lastNamePattern IS NULL OR last_name = :lastNamePattern) \n"
            + "  AND (:gender IS NULL OR gender_str = :gender) \n"
            + "  AND (:id IS NULL OR id = :id)")
    Iterable<EmployeeEntity> search(@Param("firstNamePattern") String firstNamePattern,
            @Param("lastNamePattern") String lastNamePattern, @Param("gender") String gender,
            @Param("birthDateFrom") java.sql.Date birthDateFrom, @Param("birthDateTo") java.sql.Date birthDateTo, @Param("id") Long id);

    @Query(nativeQuery = true, value = "select e.id, e.birth_date, e.first_name, e.last_name, \n"
            + "e.hire_date, e.source, e.gender_str FROM employee e JOIN department_employee de \n"
            + "ON e.id = de.employee_id JOIN department d ON d.id = de.department_id \n"
            + "WHERE (:firstNamePattern IS NULL OR e.first_name = :firstNamePattern) \n"            
            + "  AND (:lastNamePattern IS NULL OR e.last_name = :lastNamePattern) \n"
            + "  AND (:gender IS NULL OR e.gender_str = :gender) \n"
            + "  AND (:birthDateFrom IS NULL OR e.birth_date >= CAST(:birthDateFrom as DATE)) \n"
            + "  AND (:id IS NULL OR e.id = :id) \n"           
            + "  AND (:birthDateTo IS NULL OR e.birth_date <= CAST(:birthDateTo as DATE)) \n"            
            + "AND de.department_id IN (:departmentIds) AND de.to_date > current_timestamp \n")
    Page<EmployeeEntity> searchEmployeesCurrentlyWorkInDepartments(@Param("id") Long id, @Param("firstNamePattern") String firstNamePattern, 
            @Param("lastNamePattern") String lastNamePattern, @Param("gender") String gender, @Param("birthDateFrom") java.sql.Date birthDateFrom,
            @Param("birthDateTo") java.sql.Date birthDateTo, @Param("departmentIds") Collection<String> departmentIds, Pageable pageable);
}
