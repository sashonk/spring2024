package ru.asocial.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentRepo {

    @Autowired
    private NamedParameterJdbcTemplate npjt;

    public List<String> listDepartments() {
        return npjt.query("select dept_name from department", (rs, rowNum) -> rs.getString("dept_name"));
    }
}
