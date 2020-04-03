create table images
(
    id      BIGSERIAL NOT NULL,
    uuid    VARCHAR(255) DEFAULT NULL,
    user_id BIGINT       DEFAULT NULL,
    url     varchar(255) DEFAULT NULL,
    s3_key  VARCHAR(255) DEFAULT NULL,
    bucket  VARCHAR(255) DEFAULT NULL,
    primary key (id),
    CONSTRAINT fk_image_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);