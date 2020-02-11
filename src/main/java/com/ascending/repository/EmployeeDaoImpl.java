package com.ascending.repository;

import com.ascending.model.Employee;
import com.ascending.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Employee save(Employee employee) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
            return employee;
        }
        catch (Exception e){
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
            return null;
        }
    }


    @Override
    public int updateEmployeeAddressByName(String name, String address) {
        Transaction transaction = null;
        int updateCount = 0;
        String hql = "UPDATE Employee as em SET em.address = :address WHERE em.name = :name";

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("name", name);
            query.setParameter("address", address);

            transaction = session.beginTransaction();
            updateCount = query.executeUpdate();
            transaction.commit();
        }
        catch (Exception e){
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        return updateCount;
    }

    @Override
    public List<Employee> getEmployees() {
        String hql = "FROM Employee";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Employee> query = session.createQuery(hql);
            return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }
        catch (Exception e){
            logger.debug(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteByName(String eplyName) {
        String hql = "DELETE Employee as e where e.name = :emplName1";
        int deletedCount = 0;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("emplName1", eplyName);
            deletedCount = query.executeUpdate();
            transaction.commit();
        }
        catch (Exception e){
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        return deletedCount >=1 ? true:false;
    }
}
