package ru.asocial.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.CreateEmployee;
import org.openapitools.model.Employee;

import ru.asocial.entity.EmployeeEntity;

public class EmployeeMapperTest {

    private static final DateTimeFormatter bdateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Test
    public void dtoToEntityTest() {
        EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);
        CreateEmployee employee = new CreateEmployee();
        employee.setFirstName("Alex");
        employee.setLastName("Bob");
        employee.setGender("F");;
        employee.setHireDate(LocalDate.parse("2014-02-01", bdateFormatter));
        employee.setBirthDate(LocalDate.parse("1987-03-02", bdateFormatter));
        EmployeeEntity entity = employeeMapper.dtoToEntity(employee);
        Assert.assertEquals(employee.getBirthDate(), entity.getBirthDate());
        Assert.assertEquals(employee.getFirstName(), entity.getFirstName());
        Assert.assertEquals(employee.getLastName(), entity.getLastName());
        Assert.assertEquals(employee.getHireDate(), entity.getHireDate());
        Assert.assertEquals(employee.getGender(), entity.getGender());
    }

    @Test
    public void entityToDtoTest() {
        EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);
        EmployeeEntity entity = new EmployeeEntity();
        entity.setFirstName("Alex");
        entity.setLastName("Bob");
        entity.setGender("M");
        entity.setHireDate(LocalDate.parse("2014-02-01", bdateFormatter));
        entity.setBirthDate(LocalDate.parse("1987-03-02", bdateFormatter));
        Employee employee = employeeMapper.entityToDto(entity);
        Assert.assertEquals(entity.getBirthDate(), employee.getBirthDate());
        Assert.assertEquals(entity.getFirstName(), employee.getFirstName());
        Assert.assertEquals(entity.getLastName(), employee.getLastName());
        Assert.assertEquals(entity.getHireDate(), employee.getHireDate());
        Assert.assertEquals(entity.getGender(), employee.getGender());
    }
}
