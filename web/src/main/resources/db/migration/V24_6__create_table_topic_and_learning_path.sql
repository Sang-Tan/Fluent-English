CREATE TABLE IF NOT EXISTS topic_and_learning_path
(
    topic_id INTEGER NOT NULL
        REFERENCES topics (id) ON DELETE CASCADE,

    learning_path_id INTEGER NOT NULL
        REFERENCES learning_paths (id) ON DELETE CASCADE,

    position INTEGER NOT NULL,

    PRIMARY KEY (topic_id, learning_path_id),

    UNIQUE (learning_path_id, position) DEFERRABLE INITIALLY DEFERRED
);
