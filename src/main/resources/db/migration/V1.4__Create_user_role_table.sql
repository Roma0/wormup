CREATE TABLE user(
    id              BIGSERIAL NOT NULL,
    name            VARCHAR (30) NOT NULL UNIQUE,
    password        VARCHAR (54),
    first_name      VARCHAR (30),
    last_name       VARCHAR (30,
    email           VARCHAR (50) NOT NULL UNIQUE,
    primary key(id)
);

CREATE TABLE role(
    id                  BIGSERIAL NOT NULL,
    name                VARCHAR (30) not null unique,
    allowed_resource    VARCHAR (200),
    allowed_read        boolean not null default false,
    allowed_create      boolean not null default false,
    allowed_update      boolean not null default false,
    allowed_delete      boolean not null default false,
    primary key(id)
);