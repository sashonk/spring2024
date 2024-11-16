package ru.asocial.repo;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.asocial.entity.Employee;
import ru.asocial.entity.EmployeeGender;

@Repository
public interface EmployeeRepo extends org.springframework.data.repository.CrudRepository<Employee, Long> {

	Iterable<Employee> findByLastNameContaining(String str);

	@Query("SELECT * \n" + "FROM employee \n" + "WHERE (:firstName IS NULL OR first_name = :firstName) \n"
			+ "  AND (:lastName IS NULL OR last_name = :lastName) \n"
			+ "  AND (birth_date >= cast(:birthDateFrom as DATE)) \n"
			+ "  AND (birth_date <= cast(:birthDateTo as DATE)) \n")
	Iterable<Employee> search(@Param("firstName") String firstName, @Param("lastName") String lastName,
			@Param("gender") EmployeeGender gender, @Param("birthDateFrom") java.sql.Date birthDateFrom,
			@Param("birthDateTo") java.sql.Date birthDateTo);
}
