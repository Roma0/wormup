package com.ascending.repository;

import com.ascending.model.Department;
import com.ascending.model.Employee;
import com.sun.javafx.binding.StringFormatter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class DepartmentDaoTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private DepartmentDao departmentDao;
    private EmployeeDao employeeDao;

    @Before
    public void setUp() {
        logger.debug("SetUp before testing ...");
        departmentDao = new DepartmentDaoImpl();
        Department d1 = new Department();
        d1.setName("test");
        departmentDao.save(d1);

        employeeDao = new EmployeeDaoImpl();
        Employee e1 = new Employee();
        e1.setName("yd");

        Employee e2 = new Employee();
        e2.setName("lu");

        e1.setDepartment(d1);
        employeeDao.save(e1);
        e2.setDepartment(d1);
        employeeDao.save(e2);
    }

    @After
    public void tearDown(){
        logger.debug("TearDown after testing ...");
        employeeDao.delete("yd");
        employeeDao.delete("lu");
        departmentDao.delete("test");
    }

    @Test
    public void getDepartmentAndEmployeesByDepartmentNameTest(){
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));
        String expectedDepartment = "test";
        Department department = departmentDao.getDepartmentAndEmployeesByDepartmentName("test");

        Assert.assertEquals(department.getName().toLowerCase(), expectedDepartment);
        Assert.assertEquals(department.getEmployees().size(), 2);
    }
    
    @Test
    public void getDepartmentsTest() {
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));
        List<Department> departments = departmentDao.getDepartments();
        int expectedNumOfDept = 5;

//        departments.forEach(dept -> System.out.println(dept));
        Assert.assertEquals(expectedNumOfDept, departments.size());
    }

//    Question1: Wrong way to test a method with another parallel method?
//    Question2: Need to getObject from the database again for assertion?
    @Test
    public void updateTest(){
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));

        departmentDao = new DepartmentDaoImpl();

        Department expectedDepartment = departmentDao.getDepartmentByName("test");
        expectedDepartment.setLocation("location Changed");

        Department department = departmentDao.update(expectedDepartment);

        Assert.assertEquals(expectedDepartment, department);
    }

    @Test
    public void getDepartmentByNameTest(){
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));
        String deptName = "test";

        departmentDao = new DepartmentDaoImpl();
        Department department = departmentDao.getDepartmentByName(deptName);

        Assert.assertEquals(deptName, department.getName());
    }

//    @Test
//    public void getDepartmentByName() {
//        String deptName = "HR";
//        Department department = departmentDao.getDepartmentByName(deptName);
//
//        logger.info(department.toString());
//        logger.info(department.getEmployees().toString());
//        logger.info(department.getEmployees().stream().findFirst().get().getAccounts().toString());
//
//        Assert.assertEquals(deptName, department.getName());
//    }
//
//    @Test
//    public void updateDepartmentLocation() {
//        String deptName = "R&D";
//        String location = "11126 Fairhaven Court, Fairfax, VA";
//        Department department = departmentDao.getDepartmentByName(deptName);
//        department.setLocation(location);
//        departmentDao.update(department);
//        department = departmentDao.getDepartmentByName(deptName);
//        Assert.assertEquals(location, department.getLocation());
//    }
//
//    @Test
//    public void getDepartmentAndEmployeesTest() {
//        String deptName = "R&D";
//        List<Object[]> resultList = departmentDao.getDepartmentAndEmployees(deptName);
//        Assert.assertEquals(2, resultList.size());
//    }
//
//    @Test
//    public void getDepartmentAndEmployeesAndAccountsTest() {
//        String deptName = "R&D";
//        List<Object[]> resultList = departmentDao.getDepartmentAndEmployeesAndAccounts(deptName);
//        Assert.assertEquals(4, resultList.size());
//    }

}
