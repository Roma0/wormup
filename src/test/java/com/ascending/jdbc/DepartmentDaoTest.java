package com.ascending.jdbc;

import com.ascending.model.Department;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DepartmentDaoTest {
    private DepartmentDao departmentDao = new DepartmentDao();
    private Department testRecord;

    @Before
    public void setUp(){
        //TODO insert data into database
        testRecord = new Department();
        departmentDao = new DepartmentDao();
        testRecord = departmentDao.save(testRecord);
    }

    @After
    public void tearDown(){
        //TODO delete data from database
        departmentDao.delete(testRecord.getId());
    }

    @Test
    public void getDepartmentsTest(){
        System.out.println("Test method 1");
        List<Department> departments = departmentDao.getDepartments();
        int expectedNumOfDept = 5;
        Assert.assertEquals(expectedNumOfDept, departments.size());
    }
}
