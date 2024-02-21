ALTER TABLE users
    DROP CONSTRAINT chk__users__current_hp,
    ADD CONSTRAINT chk__users__current_hp CHECK (current_hp >= 0.0 AND current_hp <= 1.0);
