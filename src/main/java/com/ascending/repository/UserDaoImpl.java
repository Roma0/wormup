package com.ascending.repository;

import com.ascending.model.User;
import com.ascending.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public User save(User user) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            session.close();
        }
        catch (Exception e){
            if(transaction != null)transaction.rollback();
            logger.error(e.getMessage(), e);
        }
        if(user !=null)logger.debug(String.format("The user %s is was insert into the table.", user.getName()));
        return user;
    }

    @Override
    public User findById(Long id) {
        String hql = "FROM User as usr WHERE usr.id = :id";

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(hql);
            query.setParameter("id", id);
            User user = query.uniqueResult();
            session.close();
            return user;
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }

        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        String hql = "FROM User as user WHERE LOWER(user.email) = :email";

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase());
            User user = query.uniqueResult();
            session.close();
            return user;
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }

        return null;
    }

    @Override
    public User getUserByCredentials(String email, String password) {
        String hql = "FROM User as u WHERE LOWER(u.email)  = :email and u.password = :password";
        logger.debug(String.format("User email %s, password %s", email, password));

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase());
            query.setParameter("password", password);
            return query.uniqueResult();
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }

        return null;
    }

    @Override
    public Void delete(User user) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.delete(user);
            logger.debug(String.format("The usr %s was deleted from table.", user.getName()));
            transaction.commit();
            session.close();
        }
        catch (Exception e){
            if(transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }
        return null;
    }
}
