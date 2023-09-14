CREATE TABLE IF NOT EXISTS admins
(
    id SERIAL PRIMARY KEY,
    created_date TIMESTAMP(6) DEFAULT now(),
    email        VARCHAR(255) UNIQUE NOT NULL,
    enabled      BOOLEAN      NOT NULL,
    full_name    VARCHAR(50) NOT NULL,
    password     VARCHAR(255) NOT NULL
);