BEGIN;

CREATE TABLE word_memos
(
    user_id    INT       NOT NULL,
    word_id    INT       NOT NULL,
    stability  REAL      NOT NULL,
    difficulty REAL      NOT NULL,
    last_study TIMESTAMP NOT NULL,
    next_study TIMESTAMP NOT NULL,
    CONSTRAINT pk__word_memos PRIMARY KEY (user_id, word_id),
    CONSTRAINT fk__word_memos__user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk__word_memos__word_id FOREIGN KEY (word_id) REFERENCES words (id)
);

CREATE INDEX idx__word_memos__next_study ON word_memos (next_study);

COMMIT;