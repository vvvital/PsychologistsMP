CREATE TABLE if not exists users
(
    id        BIGSERIAL NOT NULL UNIQUE,
    email     VARCHAR   NOT NULL UNIQUE,
    password  VARCHAR   NOT NULL,
    firstName VARCHAR   NOT NULL,
    lastName  VARCHAR,
    role      VARCHAR   NOT NULL,
    location  VARCHAR
)