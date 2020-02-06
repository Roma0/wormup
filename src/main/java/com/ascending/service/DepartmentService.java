package com.ascending.service;

import com.ascending.model.Department;
import com.ascending.repository.DepartmentDao;
import com.ascending.repository.DepartmentDaoImpl;

import java.util.List;

public class DepartmentService {
    private DepartmentDao departmentDao = new DepartmentDaoImpl();

    public Department save(Department department){
        return departmentDao.save(department);
    }

    public Department update(Department department){
        return departmentDao.update(department);
    }

    public boolean delete(String deptName){
        return departmentDao.delete(deptName);
    }
    
    public List<Department> getDepartments(){
        return  departmentDao.getDepartments();
    }

    public Department getDepartmentsAndEmployeesByDepartmentName(String emplName){
        return  departmentDao.getDepartmentsAndEmployeesByDepartmentName(emplName);
    }
}
