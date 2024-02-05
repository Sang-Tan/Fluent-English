CREATE TABLE users
(
    id       SERIAL       NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NOT NULL,
    CONSTRAINT pk__users PRIMARY KEY (id),
    CONSTRAINT uq__users__email UNIQUE (email)
);