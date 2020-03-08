CREATE TABLE users_role (
    user_id    BIGINT NOT NULL,
    role_id    BIGINT NOT NULL
);
ALTER TABLE users_role
    ADD CONSTRAINT users_fk FOREIGN KEY (user_id)
        REFERENCES users ( id );
ALTER TABLE users_role
    ADD CONSTRAINT role_fk FOREIGN KEY ( role_id )
        REFERENCES role ( id );