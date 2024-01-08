CREATE TABLE IF NOT EXISTS lessons
(
    id SERIAL PRIMARY KEY,

    topic_id INTEGER NOT NULL
        REFERENCES topics(id) ON DELETE CASCADE,

    position INTEGER NOT NULL,

    name VARCHAR(100) NOT NULL,

    core_skill VARCHAR(10) NOT NULL
        CONSTRAINT lessons__core_skill__check_constant
        CHECK ( core_skill IN ('GRAMMAR', 'READING', 'LISTENING') ),

    difficulty SMALLINT NOT NULL
        CONSTRAINT lessons__difficulty__in_range
        CHECK ( difficulty >= 1 AND difficulty <= 3 ),

    is_public BOOLEAN NOT NULL DEFAULT FALSE,

    UNIQUE (topic_id, position) DEFERRABLE INITIALLY DEFERRED
);
