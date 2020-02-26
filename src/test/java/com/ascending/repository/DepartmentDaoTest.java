package com.ascending.repository;

import com.ascending.ApplicationBootstrap;
import com.ascending.model.Department;
import com.ascending.model.Employee;
import org.hibernate.mapping.Collection;
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
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class DepartmentDaoTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired DepartmentDao departmentDao;
    @Autowired EmployeeDao employeeDao;

//    private DepartmentDao departmentDao = new DepartmentDaoImpl();
//    private EmployeeDao employeeDao = new EmployeeDaoImpl();

    private String deptName = "test";
    private String emName1 = "yd";
    private String emName2 = "lu";

    @Before
    public void setUp() {
        logger.debug("SetUp before testing ...");
        Department d1 = new Department();
        d1.setName(deptName);
        assert (0 != departmentDao.save(d1).getId());

        Employee e1 = new Employee();
        e1.setName(emName1);
        e1.setDepartment(d1);
        assert (0 != employeeDao.save(e1, deptName).getId());

        Employee e2 = new Employee();
        e2.setName(emName2);
        e2.setDepartment(d1);
        assert (0 != employeeDao.save(e2, deptName).getId());
    }

    @After
    public void tearDown() {
        logger.debug("TearDown after testing ...");
        assert (employeeDao.deleteByName(emName1));
        assert (employeeDao.deleteByName(emName2));
        assert (departmentDao.delete(deptName));
    }

    @Test
    public void update(){
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));

        departmentDao = new DepartmentDaoImpl();

        Department expectedDepartment = departmentDao.getDepartmentByName(deptName);
        expectedDepartment.setLocation("location Changed");

        Department department = departmentDao.update(expectedDepartment);

        Assert.assertEquals(expectedDepartment, department);
    }


    @Test
    public void getDepartments() {
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));
        List<Department> departments = departmentDao.getDepartments();

        int expectedNumOfDept = 5;
        Assert.assertEquals(expectedNumOfDept, departments.size());
    }

    @Test
    public void getDepartmentsWithChildren() {
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));
        List<Department> departments = departmentDao.getDepartmentsWithChildren();

        int expectedNumOfDept = 5;
        Assert.assertEquals(expectedNumOfDept, departments.size());

        int expectedNumOfEmployee = 6;
//        departments.forEach(dept -> System.out.println(dept.getEmployees()));
        int sum = departments.stream().map(department ->
                department.getEmployees().size()).collect(Collectors.summingInt(Integer::intValue));
        Assert.assertEquals(expectedNumOfEmployee, sum);
    }

 //   Question1: Wrong way to test a method with another parallel method?
 //   Question2: Need to getObject from the database again for assertion?


    @Test
    public void getDepartmentByName() {
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));

        departmentDao = new DepartmentDaoImpl();
        Department department = departmentDao.getDepartmentByName(deptName);

        Assert.assertEquals(deptName, department.getName());
        Assert.assertEquals(2, department.getEmployees().size());
    }
}
