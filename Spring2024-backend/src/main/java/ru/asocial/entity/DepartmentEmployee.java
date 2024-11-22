package ru.asocial.entity;

import java.time.LocalDate;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "department_employee")
public class DepartmentEmployee {
	
	private AggregateReference<EmployeeEntity, Long> employeeId;
	
	private AggregateReference<Department, String> departmentId;
	
	@Column(value = "from_date")
	private LocalDate fromDate;
	
	@Column(value = "to_date")
	private LocalDate toDate;

	public AggregateReference<EmployeeEntity, Long> getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(AggregateReference<EmployeeEntity, Long> employeeId) {
		this.employeeId = employeeId;
	}

	public AggregateReference<Department, String> getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(AggregateReference<Department, String> departmentId) {
		this.departmentId = departmentId;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}	
}
