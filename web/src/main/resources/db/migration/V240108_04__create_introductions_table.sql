CREATE TABLE IF NOT EXISTS introductions
(
    exercise_id INTEGER PRIMARY KEY
        REFERENCES exercises (id) ON DELETE CASCADE,

    content TEXT NOT NULL
);