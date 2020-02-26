package com.ascending.repository;

import com.ascending.model.Account;
import com.ascending.model.Employee;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeeDaoTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private EmployeeDao employeeDao = new EmployeeDaoImpl();
    private AccountDao accountDao = new AccountDaoImpl();
    private String deptName = "CS";
    private String emName1 = "yd";
    private String emName2 = "fx";
    private String email = "Email@test.com";
//    private String accountType = "testAccountType";

    @Before
    public void setUp(){
        logger.debug("SetUp before testing ...");

        Employee e1 = new Employee();
        e1.setName(emName1);
        e1.setEmail(email);
        e1 = employeeDao.save(e1, deptName);
        assert (0 != e1.getId());

        Employee e2 = new Employee();
        e2.setName(emName2);
        e2 = employeeDao.save(e2, deptName);
        assert (0 != e2.getId());

    }

    @After
    public void tearDown(){
        logger.debug("TearDown after testing ...");

        assert (employeeDao.deleteByName(emName1));
        assert (employeeDao.deleteByName(emName2));
    }

    @Test
    public void getEmployees(){
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));

        int expectedNum = 6;
        List<Employee> employeeList = employeeDao.getEmployees();

        Assert.assertEquals(expectedNum, employeeList.size());
    }

    @Test
    public void updateEmployeeAddressByName(){
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));

        String address = "location changed";
        int expectedCount = 1;

        Assert.assertEquals(expectedCount, employeeDao.updateEmployeeAddressByName(emName1, address));
        Assert.assertEquals(address,employeeDao.getEmployeeByName(emName1).getAddress());
    }

    @Test
    public void getEmployeeByName(){
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));

        Assert.assertEquals(email, employeeDao.getEmployeeByName(emName1).getEmail());
    }
}
