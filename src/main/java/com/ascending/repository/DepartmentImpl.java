package com.ascending.repository;

import com.ascending.model.Department;
import com.ascending.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DepartmentImpl implements DepartmentDao {
//    private SessionFactory sessionFactory;
    private Logger logger= LoggerFactory.getLogger(getClass());

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
            logger.error(e.getMessage());
        }
        return null;
    }

//    @Override
//    public Department update(Department department) {
//        return null;
//    }

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
        return deletedCount >=1 ? true:false;
    }

    @Override
    public List<Department> getDepartment() {
        String hql = "FROM Department";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Department> query = session.createQuery(hql);
            return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }
    }
}
