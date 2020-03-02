package com.ascending.repository;

import com.ascending.model.Account;
import com.ascending.model.Department;
import com.ascending.model.Employee;
import com.ascending.model.User;
import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HibernateMappingTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void mappingDepartmentClassTest() {
        Department department = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            department = session.get(Department.class, 1L);
//            logger.info(department.toString()+"test1");
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        Assert.assertNotNull(department);
    }


//    @Test
//    public void mappingTest() {
//        String hql = "FROM Department";
//        List<Department> departments = null;
//
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Query<Department> query = session.createQuery(hql);
//            departments = query.list();
//        }
//        catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//
////        departments.forEach(dept -> logger.info(dept.toString()+"test2"));
//
//        Assert.assertNotNull(departments);
//    }
//}
    @Test
    public void mappingEmployeeClassTest(){
        Employee employee = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            employee = session.get(Employee.class, 1L);
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        Assert.assertNotNull(employee);
    }

//    @Test
//    public void mappingAccountClassTest(){
//        Account account = null;
//        try(Session session = HibernateUtil.getSessionFactory().openSession()){
//            account = session.get(Account.class, 1L);
//        }
//        catch (Exception e){
//            logger.error(e.getMessage());
//        }
//        Assert.assertNotNull(account);
//    }
//}

    @Test
    public void mappingTest() {
        String hql = "FROM Account";
        List<Account> accounts = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Account> query = session.createQuery(hql);
            accounts = query.list();
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

//        accounts.forEach(account -> logger.info(account.toString()+"test2"));

        Assert.assertNotNull(accounts);
    }

    @Test
    public void mappingUsersClass(){
        String hql = "FROM User";
        List<User> userList = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> query = session.createQuery(hql);
            userList = query.list();
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }

        Assert.assertNotNull(userList);
    }
}