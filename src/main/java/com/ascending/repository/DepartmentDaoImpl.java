package com.ascending.repository;

import com.ascending.model.Account;
import com.ascending.model.Department;
import com.ascending.model.Employee;
import com.ascending.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {
    private Logger logger = LoggerFactory.getLogger(getClass());

//    No annotation @OneToMany(with "cascade = CascadeType.REMOVE" parameter)
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
        if (department != null)logger.debug("The department was inserted into database");
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
        }
        return null;
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
            transaction.commit();
        }
        catch (Exception e){
            if (transaction != null) transaction.rollback();
            logger.error(String.format("Failure to delete record", deptName), e.getMessage(), e);
        }

        logger.debug(String.format("The department %s was deleted", deptName));
        return deletedCount >= 1 ? true:false;
    }

    @Override
    public List<Department> getDepartments() {
        String hql = "FROM Department";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Department> query = session.createQuery(hql);
            return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }
        catch (Exception e){
            logger.debug(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Department> getDepartmentsWithChildren() {
        String hql = "FROM Department as dept left join fetch dept.employees as em left join fetch em.accounts";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Department> query = session.createQuery(hql);
            return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//            session.close();
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Department getDepartmentByName(String deptName) {
        String hql = "From Department as dept left join fetch dept.employees as em " +
                "left join fetch em.accounts " + "WHERE lower(dept.name)= :deptName";

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("deptName", deptName.toLowerCase());
            return query.uniqueResult();
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

//    @Override
//    public Department getDepartmentAndEmployeesByDepartmentName(String deptName) {
//        if (deptName == null) return null;
//        Department result;
//        String hql = "FROM Department as dept left join fetch dept.employees where lower(dept.name) = :deptName1";
//        try (Session session = HibernateUtil.getSessionFactory().openSession()){
//            Query<Department> query = session.createQuery(hql);
//            query.setParameter("deptName1", deptName.toLowerCase());
//            result = query.uniqueResult();
//            return result;
//        }
//        catch (Exception e){
//            logger.debug(e.getMessage());
//        }
//        return null;
//    }

//    @Override
//    public List<Object[]> getDepartmentAndEmployees(String deptName) {
//        if (deptName == null) return null;
//
//        String hql = "FROM Department as dept left join fetch dept.employees as em WHERE lower(dept.name) = :deptName";
//
//        try (Session session = HibernateUtil.getSessionFactory().openSession()){
//            Query query = session.createQuery(hql);
//            query.setParameter("deptName", deptName.toLowerCase());
//
//            List<Object[]> resultList = query.list();
//
//            for (Object[] obj:resultList){
//                logger.debug(((Department)obj[0]).toString());
//                logger.debug(((Employee)obj[1]).toString());
//            }
//
//            return resultList;
//        }
//        catch (Exception e){
//            logger.error(e.getMessage());
//        }
//        return null;
//    }

//    @Override
//    public List<Object[]> getDepartmentAndEmployeesAndAccounts(String deptName) {
//        String hql = "FROM Department as dept left join fetch dept.employees as em " +
//                     "left join fetch em.accounts " + "WHERE lower(dept.name) = :deptName";
//
//        try (Session session = HibernateUtil.getSessionFactory().openSession()){
//            Query query = session.createQuery(hql);
//            query.setParameter("deptName", deptName.toLowerCase());
//
//            List<Object[]> resultList = query.list();
//
//            for (Object[] obj:resultList){
//                logger.debug(((Department)obj[0]).toString());
//                logger.debug(((Employee)obj[1]).toString());
//                logger.debug(((Account)obj[2]).toString());
//            }
//            return resultList;
//        }
//        catch (Exception e){
//            logger.error(e.getMessage());
//        }
//        return null;
//    }


}
