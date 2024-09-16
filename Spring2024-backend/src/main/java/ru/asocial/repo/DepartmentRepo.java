package ru.asocial.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DepartmentRepo {

    @Autowired
    private NamedParameterJdbcTemplate npjt;

    public List<Object> listDepartments() {
        return npjt.query("select id, dept_name from department", (rs, rowNum) -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", rs.getString("id"));
            m.put("department_name", rs.getString("dept_name"));
            return m;
        });
    }
}
