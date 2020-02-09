package com.ascending.repository;

import com.ascending.model.Department;

import java.util.List;

public interface DepartmentDao {
    Department save(Department department);
    Department update(Department department);
    List<Department> getDepartments();
    boolean delete(String deptName);
    Department getDepartmentByName(String deptName);
    Department getDepartmentAndEmployeesByDepartmentName(String deptName);
}
