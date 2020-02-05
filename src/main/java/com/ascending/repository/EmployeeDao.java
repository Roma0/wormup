package com.ascending.repository;

import com.ascending.model.Employee;

import java.util.List;

public interface EmployeeDao {
    Employee save(Employee employee);
    Employee update(Employee employee);
    List<Employee> getEmployee();
    boolean delete(String eplyName);
}
