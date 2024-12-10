package ru.asocial.repo;

import org.springframework.stereotype.Repository;

import ru.asocial.entity.DepartmentEntity;

@Repository
public interface DepartmentRepo extends org.springframework.data.repository.CrudRepository<DepartmentEntity, String> {

	/*
	 * @Autowired private NamedParameterJdbcTemplate npjt;
	 * 
	 * public List<Object> listDepartments() { return
	 * npjt.query("select id, dept_name from department", (rs, rowNum) -> {
	 * Map<String, Object> m = new HashMap<>(); m.put("id", rs.getString("id"));
	 * m.put("department_name", rs.getString("dept_name")); return m; }); }
	 */
}
