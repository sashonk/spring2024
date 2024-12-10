package ru.asocial.entity;

import java.io.Serializable;
import java.util.Objects;

public class DepartmentEmployeeComposite implements Serializable {
    
    private String departmentId;
    
    private Long employeeId;
    
    public String getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    public Long getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(departmentId, employeeId);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DepartmentEmployeeComposite other = (DepartmentEmployeeComposite) obj;
        return Objects.equals(departmentId, other.departmentId) && Objects.equals(employeeId, other.employeeId);
    }        
}
