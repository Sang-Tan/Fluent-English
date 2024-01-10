CREATE TABLE IF NOT EXISTS lesson_and_learning_path
(
    lesson_id INTEGER NOT NULL
        REFERENCES lessons (id) ON DELETE CASCADE,

    learning_path_id INTEGER NOT NULL
        REFERENCES learning_paths (id) ON DELETE CASCADE,

    position INTEGER NOT NULL,

    PRIMARY KEY (lesson_id, learning_path_id),

    UNIQUE (learning_path_id, position) DEFERRABLE INITIALLY DEFERRED
);
