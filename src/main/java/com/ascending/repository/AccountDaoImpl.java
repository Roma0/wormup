package com.ascending.repository;

import com.ascending.model.Account;
import com.ascending.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class AccountDaoImpl implements AccountDao{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Account save(Account account) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
            return account;
        }
        catch (Exception e){
            if(transaction != null)transaction.rollback();
            logger.debug(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Account> getAccounts() {
        String hql = "From Account";
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Account> query = session.createQuery(hql);
            return query.list();
        }
        catch (Exception e){
            logger.debug(e.getMessage());
            return null;
        }

    }

    @Override
    public Account getAccountById(long id) {
        String hql = "FROM Account as acc WHERE acc.id = :id";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Account> query = session.createQuery(hql);
            query.setParameter("id", id);
            return query.uniqueResult();
        }
        catch (Exception e){
            logger.debug(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        Transaction transaction = null;
        String hql = "DELETE Account as acc WHERE acc.id = :id";
        int deletedCount = 0;

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            deletedCount = query.executeUpdate();
            transaction.commit();
        }
        catch (Exception e){
            if (transaction != null)transaction.rollback();
            logger.debug(e.getMessage());
        }
        return deletedCount >=1 ? true:false;
    }

    @Override
    public Account updateBalanceById(long id, double balance) {
//        String hql = "UPDATE Account as acc SET acc.balance = :balance WHERE acc.id = :id";
        Transaction transaction = null;
//        int updatedCount = 0;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Account account = session.get(Account.class, id);
            account.setBalance(balance);
            session.update(account);
            transaction.commit();
            return account;
        }
        catch (Exception e){
            if (transaction != null)transaction.rollback();
            logger.debug(e.getMessage());
        }
        return null;
    }
}
