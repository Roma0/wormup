package com.ascending.service;

import com.ascending.model.Department;
import com.ascending.repository.DepartmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired private DepartmentDao departmentDao;

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

    public List<Department> getDepartmentsWithChildren(){ return  departmentDao.getDepartmentsWithChildren(); }

    public Department getDepartmentByName(String deptName){ return departmentDao.getDepartmentByName(deptName); }

}
