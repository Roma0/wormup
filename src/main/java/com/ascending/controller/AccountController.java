package com.ascending.controller;

import com.ascending.model.Account;
import com.ascending.model.Employee;
import com.ascending.service.AccountService;
import com.ascending.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired AccountService accountService;
    @Autowired EmployeeService employeeService;

    /**
     * POST /accounts?enName=value
     * @param account
     * @param employeeName
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Account createAccount(@RequestParam(name = "emName") String employeeName, @RequestBody Account account){

        Account account1 = accountService.save(account, employeeName);
        return account1;
    }

    /**
     * GET /accounts
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Account> getAccounts(){
        List<Account> accountList = accountService.getAccounts();
        return accountList;
    }

    /**
     * GET /accounts/{id}
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Account getAccountById(@PathVariable long id){
        Account account = accountService.getAccountById(id);
        return account;
    }

    /**
     * GET /accounts/?employeeName=value
     * @param name
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Set<Account> getAccountsByEmployeeName(@RequestParam(value = "employeeName") String name){

        Employee employee = employeeService.getEmployeeWithAccountsByName(name);
        Set<Account> accountSet= employee.getAccounts();
        return accountSet;
    }

    /**
     * PUT /accounts?id=value
     * @param id
     * @param balance
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Account updateBalanceById(@PathVariable long id, @RequestBody double balance){
        Account account = accountService.updateBalanceById(id, balance);
        return account;
    }

}
