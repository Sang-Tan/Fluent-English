CREATE TABLE IF NOT EXISTS introductions
(
    lesson_id INTEGER PRIMARY KEY
        REFERENCES lessons (id) ON DELETE CASCADE,

    content TEXT NOT NULL
);