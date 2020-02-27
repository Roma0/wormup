package com.ascending.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    public Account(){}
    public Account(String accountType, double balance){
        this.accountType = accountType;
        this.balance = balance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private long id;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "balance")
    private double balance;

//    @Column(name = "employee_id")
//    private long employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }
    
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

//    public long getEmployeeId() {
//        return employeeId;
//    }
//
//    public void setEmployeeId(long employeeId) {
//        this.employeeId = employeeId;
//    }

    public Employee getEmployee(){return employee;}

    public void setEmployee(Employee employee){this.employee = employee;}

    @Override
    public String toString() {
        return String.format("[%d | %s | %.2f]", id, accountType, balance);
    }
}