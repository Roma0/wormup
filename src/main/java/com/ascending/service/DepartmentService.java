package com.ascending.service;

import com.ascending.model.Department;
import com.ascending.repository.DepartmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public Department getDepartmentById(long id){
        List<Department> departments = departmentDao.getDepartments().stream().filter(e -> e.getId()==id).collect(Collectors.toList());

        return departments.get(0);
    }

}
