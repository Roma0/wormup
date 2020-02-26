package com.ascending.service;

import com.ascending.ApplicationBootstrap;
import com.ascending.model.Employee;
import com.ascending.repository.AccountDao;
import com.ascending.repository.AccountDaoImpl;
import com.ascending.repository.EmployeeDao;
import com.ascending.repository.EmployeeDaoImpl;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class EmployeeServiceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired private EmployeeService employeeService;
//    @Autowired private AccountDao accountDao;

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
        e1 = employeeService.save(e1, deptName);
        assert (0 != e1.getId());

        Employee e2 = new Employee();
        e2.setName(emName2);
        e2 = employeeService.save(e2, deptName);
        assert (0 != e2.getId());

    }

    @After
    public void tearDown(){
        logger.debug("TearDown after testing ...");

        assert (employeeService.deleteByName(emName1));
        assert (employeeService.deleteByName(emName2));
    }

    @Test
    public void getEmployees(){
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));

        int expectedNum = 6;
        List<Employee> employeeList = employeeService.getEmployees();

        Assert.assertEquals(expectedNum, employeeList.size());
    }

    @Test
    public void updateEmployeeAddressByName(){
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));

        String address = "location changed";
        int expectedCount = 1;

        Assert.assertEquals(expectedCount, employeeService.updateEmployeeAddressByName(emName1, address));
        Assert.assertEquals(address,employeeService.getEmployeeByName(emName1).getAddress());
    }

    @Test
    public void getEmployeeByName(){
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));

        Assert.assertEquals(email, employeeService.getEmployeeByName(emName1).getEmail());
    }
}
