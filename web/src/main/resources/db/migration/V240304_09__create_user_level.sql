BEGIN;

CREATE TABLE user_levels
(
    level             INTEGER NOT NULL,
    exp_to_next_level INTEGER NOT NULL,
    base_hp           INTEGER NOT NULL,
    base_shield       INTEGER NOT NULL,
    CONSTRAINT pk__user_levels PRIMARY KEY (level),
    CONSTRAINT chk__user_levels__level CHECK (level > 0),
    CONSTRAINT chk__user_levels__exp_to_next_level CHECK (exp_to_next_level > 0),
    CONSTRAINT chk__user_levels__base_hp CHECK (base_hp > 0),
    CONSTRAINT chk__user_levels__base_shield CHECK (base_shield > 0)
);

ALTER TABLE users
    ADD COLUMN level INTEGER DEFAULT NULL;

ALTER TABLE users
    ADD COLUMN experience INTEGER NOT NULL DEFAULT 0;

COMMIT;