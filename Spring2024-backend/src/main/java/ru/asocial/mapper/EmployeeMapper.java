package ru.asocial.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.CreateEmployee;
import org.openapitools.model.Employee;

import ru.asocial.entity.EmployeeEntity;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee entityToDto(EmployeeEntity entity);

    EmployeeEntity dtoToEntity(CreateEmployee dto);
}
