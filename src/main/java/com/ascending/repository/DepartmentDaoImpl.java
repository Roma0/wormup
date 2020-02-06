package com.ascending.repository;

import com.ascending.model.Department;
import com.ascending.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DepartmentDaoImpl implements DepartmentDao {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Department save(Department department) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(department);
            transaction.commit();
            return department;
        }
        catch (Exception e){
            if (transaction != null) transaction.rollback();
            logger.error("Failure to insert record", e);
        }
        if (department != null)logger.debug("Tje department was inserted into database");
            return null;
    }

    @Override
    public Department update(Department department) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(department);
            transaction.commit();
            return department;
        }
        catch (Exception e){
            if (transaction != null) transaction.rollback();
            logger.error("Failure to update record", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(String deptName) {
        String hql = "DELETE Department as d where d.name = :deptname1";
        int deletedCount = 0;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query<Department> query = session.createQuery(hql);
            query.setParameter("deptname1", deptName);
            deletedCount = query.executeUpdate();
        }
        catch (Exception e){
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }
        return deletedCount >= 1 ? true:false;
    }

    @Override
    public List<Department> getDepartments() {
        String hql = "FROM Department";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Department> query = session.createQuery(hql);
            return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }
    }

    @Override
    public Department getDepartmentsAndEmployeesByDepartmentName(String deptName) {
        if (deptName == null) return null;
        Department result;
        String hql = "FROM Department as dept left join fetch dept.employees where lower(dept.name) = :deptName1";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Department> query = session.createQuery(hql);
            query.setParameter("deptName1", deptName.toLowerCase());
            result = query.uniqueResult();

        }
        return result;
    }
}
