package ru.asocial.repo;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.asocial.entity.EmployeeEntity;
import ru.asocial.entity.EmployeeGender;

@Repository
public interface EmployeeRepo extends org.springframework.data.repository.CrudRepository<EmployeeEntity, Long> {

	Iterable<EmployeeEntity> findByLastNameContaining(String str);

	@Query("SELECT * \n" + "FROM employee \n" + "WHERE (:firstName IS NULL OR first_name = :firstName) \n"
			+ "  AND (:lastName IS NULL OR last_name = :lastName) \n"
			+ "  AND (cast(gender as varchar) = :gender) \n"
			+ "  AND (birth_date >= cast(:birthDateFrom as DATE)) \n"
			+ "  AND (birth_date <= cast(:birthDateTo as DATE)) \n")
	Iterable<EmployeeEntity> search(@Param("firstName") String firstName, @Param("lastName") String lastName,
			@Param("gender") String gender, @Param("birthDateFrom") java.sql.Date birthDateFrom,
			@Param("birthDateTo") java.sql.Date birthDateTo);
	
	@Query("select * from employee where ")
	Iterable<EmployeeEntity> findEmployeesForProcessing();
}
