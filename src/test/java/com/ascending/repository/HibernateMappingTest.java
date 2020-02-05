package com.ascending.repository;

import com.ascending.model.Department;
import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HibernateMappingTest {
    @Test
    public void mappingClassTest() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Department department = session.get(Department.class, 1L);
            Assert.assertNotNull(department);
        }
    }
}

//public class HibernateMappingTest {
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Test
//    public void mappingTest() {
//        String hql = "FROM Department";
//        List<Department> departments = null;
//
//        try (
//                Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Query<Department> query = session.createQuery(hql);
//            departments = query.list();
//        }
//        catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//
//        departments.forEach(dept -> logger.info(dept.toString()));
//
//        Assert.assertNotNull(departments);
//    }
//}
