package com.ascending.repository;

import com.ascending.model.Department;
import com.ascending.model.Employee;

import java.util.List;
import java.util.Set;

public interface EmployeeDao {
    Employee save(Employee employee);
    int updateEmployeeAddressByName(String name, String address);
    List<Employee> getEmployees();
    boolean deleteByName(String employeeName);
}
