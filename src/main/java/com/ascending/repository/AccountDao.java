package com.ascending.repository;

import com.ascending.model.Account;

import java.util.List;

public interface AccountDao {
    Account save(Account account);
    List<Account> getAccounts();
    Account getAccountById();
    boolean deleteById(long id);
//    boolean deleteByEmployeeId(long id);
    Account updateBalanceById(double balance);
}
