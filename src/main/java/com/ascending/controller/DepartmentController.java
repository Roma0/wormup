package com.ascending.controller;

import com.ascending.model.Department;
import com.ascending.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = { "/departments", "/depts" })
public class DepartmentController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired private DepartmentService departmentService;

    /**
     * GET /departments, /depts
     * @return
     */
    @RequestMapping( method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Department> getDepartments(){
        List<Department> departments = departmentService.getDepartments();
        return departments;
    }

    /**
     * POST /departments/body, /depts/body
     * @param department
     * @return
     */
    @RequestMapping(value = "/body", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Department createDepartment(@RequestBody Department department){
        Department dept = departmentService.save(department);
        return dept;
    }

    /**
     * GET /departments/{id}, /depts/{id}
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Department getDepartmentById(@PathVariable long id){
        Department dept = departmentService.getDepartmentById(id);
        return dept;
    }

    /**
     * GET /departments/?deptName=value, /depts/?deptName=value
     * @param name
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Department getDepartmentByName(@RequestParam(name = "deptName") String name){
        Department dept = departmentService.getDepartmentByName(name);
        return dept;
    }

    /**
     * PUT /departments, /depts
     * @param department
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Department updateDepartment(@RequestBody Department department){
        Department dept = departmentService.update(department);
        return dept;
    }

//    /**
//     * GET /departments/number
//     * @return
//     */
//    @RequestMapping(value = {"/number"}, method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE})
//    public Map<String, Object> getSampleJson(){
//        List<Department> departments = departmentService.getDepartments();
//
//        Map<String, Object> m = new HashMap<>();
//        m.put("Departments Number", departments.size());
//        return m;
//    }
}
