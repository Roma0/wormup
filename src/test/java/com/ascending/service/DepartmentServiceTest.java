package com.ascending.service;

import com.ascending.ApplicationBootstrap;
import com.ascending.model.Department;
import com.ascending.repository.DepartmentDao;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class DepartmentServiceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    DepartmentService departmentService;

    @Before
    public void setUp() {
        logger.debug("SetUp before testing ...");
        Department d1 = new Department();
        d1.setName("test");
        assert (0 != departmentService.save(d1).getId());
    }

    @After
    public void tearDown(){
        logger.debug("TearDown after testing ...");
        assert (departmentService.delete("test"));
    }

    @Test
    public void getDepartmentsTest() {
        logger.debug(String.format("Testing %s ...", this.getClass().getName()));
        List<Department> departments = departmentService.getDepartments();
        int expectedNumOfDept = 5;

//        departments.forEach(dept -> System.out.println(dept));
        Assert.assertEquals(expectedNumOfDept, departments.size());
    }
}
