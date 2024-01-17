BEGIN;
ALTER TABLE exercises
    ADD COLUMN introduction TEXT;

UPDATE exercises
SET introduction = introductions.content
FROM introductions
WHERE exercises.id = introductions.exercise_id;

--drop introductions table
DROP TABLE introductions;
COMMIT;
