package com.ascending.controller;

import com.ascending.model.Employee;
import com.ascending.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired EmployeeService employeeService;

    /**
     * POST /employees
     * @param employee
     * @param deptName
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Employee createEmployee(@RequestBody Employee employee, @RequestBody String deptName){
        Employee em = employeeService.save(employee, deptName);
        return em;
    }

    /**
     * GET /employees
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Employee> getEmployees(){
        List<Employee> employeeList = employeeService.getEmployees();
        return employeeList;
    }

    /**
     * GET /employees/id
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Employee getEmployeeById(@PathVariable long id){
        Employee em = employeeService.getEmployeeById(id);
        return em;
    }

    /**
     * GET /employees/?name=value
     * @param name
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Employee getEmployeeByName(@RequestParam String name){
        Employee em = employeeService.getEmployeeByName(name);
        return em;
    }

    /**
     * PUT /employees
     * @param name
     * @param address
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Employee updateEmployeeAddressByName(@RequestBody String name, @RequestBody String address){
        int updateNum = 0;
        Employee em = new Employee();
        updateNum = employeeService.updateEmployeeAddressByName(name, address);
        if(updateNum != 0)em = employeeService.getEmployeeByName(name);
        return em;
    }
}
