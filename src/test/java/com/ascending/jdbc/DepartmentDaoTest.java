package com.ascending.jdbc;

import com.ascending.model.Department;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class DepartmentDaoTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private DepartmentDao departmentDao;
    private Department testRecord;

    @Before
    public void setUp(){
        logger.debug("Executing beforeTest...");
        departmentDao = new DepartmentDao();
        testRecord = new Department();
        testRecord.setName("DC");
        testRecord.setDescription("DataBase Center");
        testRecord.setLocation("Room 104, 999 Washington Ave. Falls Church, VA");
        testRecord = departmentDao.save(testRecord);
        logger.debug("Automatic generated Id: " + testRecord.getId());
    }

    @After
    public void tearDown(){
        logger.debug("Executing afterTest...");
        departmentDao.delete(testRecord.getId());
        logger.debug("Have deleted the record with Id: " + testRecord.getId());
    }

    @Test
    public void getDepartmentsTest(){
        System.out.println("Test method 1");
        List<Department> departments = departmentDao.getDepartments();
        int expectedNumOfDept = 1;
        Assert.assertEquals(expectedNumOfDept, departments.size());
    }

//    public static void main(String[] args){
//        DepartmentDaoTest departmentDaoTest = new DepartmentDaoTest();
//        departmentDaoTest.getDepartmentsTest();
//    }
}
