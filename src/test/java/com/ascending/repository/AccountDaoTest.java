package com.ascending.repository;

import com.ascending.model.Account;
import com.ascending.model.Employee;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AccountDaoTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private AccountDao accountDao = new AccountDaoImpl();

    private String accountType = "test";
    private double balance = 19.99;
    private Account a1 = new Account(accountType, balance);

    private String employeeName = "rhang";
    private long accountId;

    @Before
    public void setUp(){
        logger.debug("SetUp before testing ...");

        accountId = 0;
        a1 = accountDao.save(a1, employeeName);
        assert (0 != a1.getId());
        accountId = a1.getId();
    }

    @After
    public void tearDown(){
        logger.debug("TearDown after testing ...");
        assert (accountDao.deleteById(accountId));
    }

    @Test
    public void getAccounts(){
        List<Account> accountList = accountDao.getAccounts();

        int expectedNumOfAccounts = 5;
        Assert.assertEquals(expectedNumOfAccounts, accountList.size());
    }

    @Test
    public void getAccountById(){
//        Employee employee = new Employee();
//        employee.setId(1);
//        Account expectedAccount = new Account("checking",999999.99);
//        expectedAccount.setId(1);
//        expectedAccount.setEmployee(employee);
        Account account = accountDao.getAccountById(accountId);
        Assert.assertEquals(accountId, account.getId());
    }


    @Test
    public void updateBalanceById(){
        double expectedBalance = 888.88;
        Account account = accountDao.updateBalanceById(accountId, expectedBalance);
        Assert.assertEquals(expectedBalance, account.getBalance(), 0.01);
    }
}
