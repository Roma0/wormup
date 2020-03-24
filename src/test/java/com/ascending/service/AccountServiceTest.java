package com.ascending.service;

import com.ascending.ApplicationBootstrap;
import com.ascending.model.Account;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class AccountServiceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired private AccountService accountService;

    private String accountType = "test";
    private double balance = 19.99;
    private Account a1 = new Account(accountType, balance);

    private String employeeName = "rhang";
    private long accountId;

    @Before
    public void setUp(){
        logger.debug("SetUp before testing ...");

        accountId = 0;
        a1 = accountService.save(a1, employeeName);
        assert (0 != a1.getId());
        accountId = a1.getId();
    }

    @After
    public void tearDown(){
        logger.debug("TearDown after testing ...");
        assert (accountService.deleteById(accountId));
    }

    @Test
    public void getAccounts(){
        List<Account> accountList = accountService.getAccounts();

        int expectedNumOfAccounts = 5;
        Assert.assertEquals(expectedNumOfAccounts, accountList.size());
    }

    @Test
    public void getAccountById(){
        Account account = accountService.getAccountById(accountId);
        Assert.assertEquals(accountId, account.getId());
    }


    @Test
    public void updateBalanceById(){
        double expectedBalance = 888.88;
        Account account = accountService.updateBalanceById(accountId, expectedBalance);
        Assert.assertEquals(expectedBalance, account.getBalance(), 0.01);
    }
}
