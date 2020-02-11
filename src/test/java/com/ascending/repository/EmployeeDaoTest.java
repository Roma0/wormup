package com.ascending.repository;

import com.ascending.model.Employee;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmployeeDaoTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Before
    public void setUp(){
        logger.debug("SetUp before testing ...");

        Employee e1 = new Employee();
        e1.setName("YD");
        e1.setAddress("location1");
        e1 = employeeDao.save(e1);
        assert (0 != e1.getId());

        Employee e2 = new Employee();
        e2.setName("FX");
        e2.setAddress("location2");
        e2 = employeeDao.save(e2);
        assert (0 != e2.getId());

//        Employee e3 = new Employee();
//        e3.setName("FX");
//        e3.setAddress("location3");
//        e3 = employeeDao.save(e3);
//        assert (0 != e3.getId());
    }

    @After
    public void tearDown(){
        logger.debug("TearDown after testing ...");

        assert (employeeDao.deleteByName("YD"));
        assert (employeeDao.deleteByName("FX"));
    }

    @Test
    public void getEmployeesTest(){
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));

        int expectedNum = 6;
        List<Employee> employeeList = employeeDao.getEmployees();

        Assert.assertEquals(expectedNum, employeeList.size());
    }

    @Test
    public void updateEmployeeAddressByNameTest(){
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));

        String name = "FX";
        String address = "location changed";
        int expectedCount = 1;

        Assert.assertEquals(expectedCount, employeeDao.updateEmployeeAddressByName(name, address));
    }
}
