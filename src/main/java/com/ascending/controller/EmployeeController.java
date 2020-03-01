package com.ascending.controller;

import com.ascending.model.Employee;
import com.ascending.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired EmployeeService employeeService;

    /**
     * POST /employees?deptName=value
     * @param employee
     * @param deptName
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Employee createEmployee(@RequestBody Employee employee, @RequestParam(name = "deptName") String deptName){
        Employee em = null;
        try {
            em = employeeService.save(employee, deptName);
        }
        catch (Exception e){
            logger.debug(String.format("The department record name of %s failed to inserted."), deptName);
            logger.debug(e.getMessage());
        }
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
     * GET /employees/employeeName?name=value
     * @param name
     * @return
     */
    @RequestMapping(value = "/emloyeeName", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Employee getEmployeeByName(@RequestParam(name = "name") String name){
        Employee em = employeeService.getEmployeeByName(name);
        return em;
    }

    /**
     * PUT /employees?name=value
     *
     * Don't use "@RequestBody" to define "String variable like this method.
     * @param name
     * @param address
     * @param address
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Employee updateEmployeeAddressByName(@RequestParam(name = "name") String name,
                                                @RequestBody String address){
        int updateNum = 0;
        Employee em;

        updateNum = employeeService.updateEmployeeAddressByName(name, address);

        if(updateNum >=1) {
            em = employeeService.getEmployeeByName(name);
        }
        else {em = null;}

        return em;
    }

//    /**
//     * Not a valid method to pass two @RequestBody
//     * PUT /employees
//     * @param name
//     * @param address
//     * @return
//     */
//    @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public Employee updateEmployeeAddressByName(@RequestBody String name, @RequestBody String address){
//        int updateNum = 0;
//        Employee em;
//
//        updateNum = employeeService.updateEmployeeAddressByName(name, address);
//        if(updateNum >=1) {
//            em = employeeService.getEmployeeByName(name);
//        }
//        else {em = null;}
//
//        return em;
//    }
}
