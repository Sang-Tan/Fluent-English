CREATE TABLE lessons(
    id SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    difficulty SMALLINT NOT NULL,
    CONSTRAINT pk__lessons PRIMARY KEY (id),
    CONSTRAINT chk__difficulty CHECK (difficulty BETWEEN 1 AND 6)
);