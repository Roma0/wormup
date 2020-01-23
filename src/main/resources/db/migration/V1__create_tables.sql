CREATE TABLE department (
    /*id                INTEGER NOT NULL default nextval('department_id_seq'), */
    id                BIGSERIAL NOT NULL,
    name              VARCHAR(30) not null unique,
    description       VARCHAR(150),
    location          VARCHAR(100)
);
ALTER TABLE department ADD CONSTRAINT department_pk PRIMARY KEY ( id );
CREATE TABLE employee (
    /*id              INTEGER NOT NULL default nextval('employee_id_seq'),*/
    id              BIGSERIAL NOT NULL,
    name            VARCHAR(30) not null unique,
    first_name      VARCHAR(30),
    last_name       VARCHAR(30),
    email           VARCHAR(50),
    address         VARCHAR(150),
    hired_date      date default CURRENT_DATE,
    department_id   bigint
);
ALTER TABLE employee ADD CONSTRAINT employee_pk PRIMARY KEY ( id );