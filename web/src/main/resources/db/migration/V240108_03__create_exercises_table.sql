CREATE TABLE IF NOT EXISTS exercises
(
    id SERIAL PRIMARY KEY,

    lesson_id INTEGER NOT NULL
        REFERENCES lessons(id) ON DELETE CASCADE,

    position INTEGER NOT NULL,

    name VARCHAR(100) NOT NULL,

    core_skill VARCHAR(10) NOT NULL
        CONSTRAINT exercises__core_skill__check_constant
        CHECK ( core_skill IN ('GRAMMAR', 'READING', 'LISTENING') ),

    difficulty SMALLINT NOT NULL
        CONSTRAINT exercises__difficulty__in_range
        CHECK ( difficulty >= 1 AND difficulty <= 3 ),

    is_public BOOLEAN NOT NULL DEFAULT FALSE,

    UNIQUE (lesson_id, position) DEFERRABLE INITIALLY DEFERRED
);
