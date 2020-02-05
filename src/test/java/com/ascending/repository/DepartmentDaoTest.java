package com.ascending.repository;

import com.ascending.model.Department;
import com.ascending.model.Employee;
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
    public void init() {
        departmentDao = new DepartmentDaoImpl();
        Department d1 = new Department();
        d1.setName("test");
        departmentDao.save(d1);

        Employee e1 = new Employee();
        e1.setName("yd");

        Employee e2 = new Employee();
        e2.setName("lu");

        e1.setDepartment(d1);
        employeeDao.save(e1);
        employeeDao.save(e2);
    }

    @Test
    public void getDepartmentsAndEmployeesByDepartmentNameTest(){
        String expectedDepartment = "HR";
        List<Department> departments = departmentDao.getDepartmentsAndEmployeesByDepartmentName("HR");

        Assert.assertTrue(departments.size() > 0);
        Department result = departments.get(0);
        Assert.assertEquals(result.getName(), expectedDepartment);
        Assert.assertEquals(result.getEmployees().size(), 2);
    }

//    @After
//    public void tearDown(){
//        employeeDao
//    }

//    @Test
//    public void getDepartmentsTest() {
//        List<Department> departments = departmentDao.getDepartments();
//        int expectedNumOfDept = 4;
//
//        departments.forEach(dept -> System.out.println(dept));
//        Assert.assertEquals(expectedNumOfDept, departments.size());
//    }

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
