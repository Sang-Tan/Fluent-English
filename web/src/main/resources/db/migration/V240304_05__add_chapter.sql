BEGIN;

CREATE TABLE chapters
(
    number          INTEGER,
    story_start_eng TEXT,
    story_start_vi  TEXT,
    story_end_eng   TEXT,
    story_end_vi    TEXT,
    CONSTRAINT pk__chapters PRIMARY KEY (number),
    CONSTRAINT chk__chapters__number CHECK (number > 0)
);

ALTER TABLE users
    ADD COLUMN chapter_num INTEGER DEFAULT NULL;
ALTER TABLE users
    ADD CONSTRAINT fk__users__chapter_num FOREIGN KEY (chapter_num) REFERENCES chapters (number);

ALTER TABLE users
    ADD COLUMN chapter_progress REAL NOT NULL DEFAULT 0;
ALTER TABLE users
    ADD CONSTRAINT chk__users__chapter_progress CHECK (chapter_progress >= 0 AND chapter_progress <= 1);

COMMIT;