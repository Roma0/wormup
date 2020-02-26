package com.ascending.service;

import com.ascending.model.Account;
import com.ascending.repository.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired private AccountDao accountDao;

    public Account save(Account account, String employeeName){return accountDao.save(account, employeeName);}

    public List<Account> getAccounts(){return accountDao.getAccounts();}

    public Account getAccountById(long id){return accountDao.getAccountById(id);}

    public boolean deleteById(long id){return accountDao.deleteById(id);}

    public Account updateBalanceById(long id, double balance){return accountDao.updateBalanceById(id, balance);}
}
