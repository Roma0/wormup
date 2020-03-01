package com.ascending.service;

import com.ascending.model.Employee;
import com.ascending.repository.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    @Autowired private EmployeeDao employeeDao;

    public Employee save(Employee employee, String deptName){
        Employee employee1 = employeeDao.save(employee, deptName);
        return employeeDao.getEmployeeById(employee1.getId());
    }

    public int updateEmployeeAddressByName(String name, String address){
        return employeeDao.updateEmployeeAddressByName(name, address);
    }


//    /*
//    How to deal Map<k, v> parameter in method's arg
//     */
//    public void updateEmployeeAddressByName(Map<String, String> nameToAddressMaps){
//        nameToAddressMaps.forEach((name, address) -> {
//            employeeDao.updateEmployeeAddressByName(name, address);
//        });
//    }

    public boolean deleteByName(String employeeName){return employeeDao.deleteByName(employeeName);}

    public List<Employee> getEmployees(){return employeeDao.getEmployees();}

    public Employee getEmployeeByName(String name){return employeeDao.getEmployeeByName(name);}

    public Employee getEmployeeById(long id){return employeeDao.getEmployeeById(id);}

    public Employee getEmployeeWithAccountsByName(String name){return employeeDao.getEmployeeWithAccountsByName(name);}
}
