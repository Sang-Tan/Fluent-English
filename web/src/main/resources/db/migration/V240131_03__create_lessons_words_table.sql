CREATE TABLE lessons__words (
    lesson_id INTEGER NOT NULL,
    word_id INTEGER NOT NULL,
    CONSTRAINT pk__lessons__words PRIMARY KEY (lesson_id, word_id),
    CONSTRAINT fk__lessons__words__lesson_id
        FOREIGN KEY (lesson_id) REFERENCES lessons (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk__lessons__words__word_id
        FOREIGN KEY (word_id) REFERENCES words (id) ON UPDATE CASCADE ON DELETE CASCADE
);