package com.ascending.model;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "balance")
    private double balance;

//    @Column(name = "employee_id")
//    private long employeeId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
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