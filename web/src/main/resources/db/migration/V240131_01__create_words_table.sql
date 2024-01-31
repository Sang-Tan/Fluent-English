CREATE TABLE words (
    id SERIAL NOT NULL,
    text VARCHAR(255) NOT NULL,
    sound JSONB,
    image JSONB,
    vietnamese_meaning VARCHAR(255),
    difficulty SMALLINT,
    CONSTRAINT pk__words PRIMARY KEY (id),
    CONSTRAINT chk__words__difficulty CHECK (difficulty BETWEEN 1 AND 6)
);