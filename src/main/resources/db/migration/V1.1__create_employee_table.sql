create table account {
    id                  bigserial not null,
    account_type        varchar (30),
    balance             int,
    employee_id         bigint,
    primary key (id)
};

ALTER TABLE account add constraint account_employee_fk foreign key (employee_id)
references employee (id);

ALTER TABLE employee add constraint employee_department_fk foreign key (department_id)
references department (id);