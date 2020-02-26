package com.ascending.service;

import com.ascending.model.Employee;
import com.ascending.repository.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired private EmployeeDao employeeDao;

    public Employee save(Employee employee, String deptName){ return employeeDao.save(employee, deptName); }

    public int updateEmployeeAddressByName(String name, String address){
        return employeeDao.updateEmployeeAddressByName(name, address);
    }

    public boolean deleteByName(String employeeName){return employeeDao.deleteByName(employeeName);}

    public List<Employee> getEmployees(){return employeeDao.getEmployees();}

    public Employee getEmployeeByName(String name){return employeeDao.getEmployeeByName(name);}
}
