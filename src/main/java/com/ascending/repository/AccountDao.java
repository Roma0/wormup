package com.ascending.repository;

import com.ascending.model.Account;

import java.util.List;

public interface AccountDao {
    Account save(Account account);
    List<Account> getAccounts();
    Account getAccountById(long id);
    boolean deleteById(long id);
//    boolean deleteByEmployeeId(long id);
    Account updateBalanceById(long id, double balance);
}
