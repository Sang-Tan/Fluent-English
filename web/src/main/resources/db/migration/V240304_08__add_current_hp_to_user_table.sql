BEGIN;

ALTER TABLE users
    ADD COLUMN current_hp REAL DEFAULT 1.0;

ALTER TABLE users
    ADD CONSTRAINT chk__users__current_hp CHECK (current_hp IN (0.0, 1.0));

COMMIT;
