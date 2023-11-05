DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE if not exists users
(
    id        BIGSERIAL NOT NULL UNIQUE,
    email     VARCHAR   NOT NULL UNIQUE,
    password  VARCHAR   NOT NULL,
    firstName VARCHAR   NOT NULL,
    lastName  VARCHAR,
    role      VARCHAR   NOT NULL,
    location  VARCHAR,
    dtype     VARCHAR
);

CREATE TABLE IF NOT EXISTS card
(
    id          BIGSERIAL NOT NULL PRIMARY KEY,
    user_id     INTEGER   NOT NULL,
    price       INTEGER,
    rating      INTEGER,
    experience  INTEGER,
    description VARCHAR,
    photoLink   VARCHAR,
    CONSTRAINT user_card_idx unique (id, user_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS categories
(
    user_id    INTEGER NOT NULL,
    categories VARCHAR NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE

);