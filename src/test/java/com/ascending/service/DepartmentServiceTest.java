package com.ascending.service;

import com.ascending.ApplicationBootstrap;
import com.ascending.model.Department;
import com.ascending.model.Employee;
import com.ascending.repository.EmployeeDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class DepartmentServiceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired DepartmentService departmentService;
    @Autowired EmployeeService employeeService;

    private String deptName = "test";
    private String emName1 = "yd";
    private String emName2 = "lu";

    @Before
    public void setUp() {
        logger.debug("SetUp before testing ...");
        Department d1 = new Department();
        d1.setName(deptName);
        assert (0 != departmentService.save(d1).getId());

        Employee e1 = new Employee();
        e1.setName(emName1);
        e1.setDepartment(d1);
        assert (0 != employeeService.save(e1, deptName).getId());

        Employee e2 = new Employee();
        e2.setName(emName2);
        e2.setDepartment(d1);
        assert (0 != employeeService.save(e2, deptName).getId());
    }

    @After
    public void tearDown() {
        logger.debug("TearDown after testing ...");
        assert (employeeService.deleteByName(emName1));
        assert (employeeService.deleteByName(emName2));
        assert (departmentService.delete(deptName));
    }

    @Test
    public void update(){
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));

        Department expectedDepartment = departmentService.getDepartmentByName(deptName);
        expectedDepartment.setLocation("location Changed");

        Department department = departmentService.update(expectedDepartment);

        Assert.assertEquals(expectedDepartment, department);
    }


    @Test
    public void getDepartments() {
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));
        List<Department> departments = departmentService.getDepartments();

        int expectedNumOfDept = 5;
        Assert.assertEquals(expectedNumOfDept, departments.size());
    }

    @Test
    public void getDepartmentsWithChildren() {
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));
        List<Department> departments = departmentService.getDepartmentsWithChildren();

        int expectedNumOfDept = 5;
        Assert.assertEquals(expectedNumOfDept, departments.size());

        int expectedNumOfEmployee = 6;
//        departments.forEach(dept -> System.out.println(dept.getEmployees()));
        int sum = departments.stream().map(department ->
                department.getEmployees().size()).mapToInt(Integer::intValue).sum();
        Assert.assertEquals(expectedNumOfEmployee, sum);
    }

    //   Question1: Wrong way to test a method with another parallel method?
    //   Question2: Need to getObject from the database again for assertion?

    @Test
    public void getDepartmentByName() {
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));

        Department department = departmentService.getDepartmentByName(deptName);

        Assert.assertEquals(deptName, department.getName());
        Assert.assertEquals(2, department.getEmployees().size());
    }

    @Test
    public void getDepartmentById(){
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));

        Department department = departmentService.getDepartmentById(1L);

        Assert.assertEquals(1L, department.getId());
    }
}
