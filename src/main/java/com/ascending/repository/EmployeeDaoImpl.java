package com.ascending.repository;

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
public class EmployeeDaoImpl implements EmployeeDao {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private  DepartmentDao departmentDao = new DepartmentDaoImpl();

    @Override
    public Employee save(Employee employee, String deptName) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Department department = departmentDao.getDepartmentByName(deptName);

            if (department != null){
                transaction = session.beginTransaction();
                employee.setDepartment(department);
                session.save(employee);
                transaction.commit();
                return employee;
            }
            else {
                logger.debug(String.format("The department %s doesn't exist"), deptName);
            }
        }
        catch (Exception e){
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        return null;
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
    public boolean deleteByName(String emName) {
        String hql = "DELETE Employee as e where e.name = :emName1";
        int deletedCount = 0;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("emName1", emName);
            deletedCount = query.executeUpdate();
            transaction.commit();
        }
        catch (Exception e){
            if (transaction != null)transaction.rollback();
            logger.error(e.getMessage());
        }
        return deletedCount >=1 ? true:false;
    }

    @Override
    public List<Employee> getEmployees() {
        String hql = "FROM Employee as em left join fetch em.accounts";
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
    public Employee getEmployeeByName(String name) {
        String hql = "FROM Employee as em left join fetch em.accounts WHERE lower(em.name) = :emName";

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("emName", name);

            return query.uniqueResult();
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }

        return null;
    }


}
