BEGIN;
-- Quizzes-Exercises
ALTER TABLE quizzes
    DROP CONSTRAINT quizzes_exercise_id_fkey;

ALTER TABLE quizzes
    ADD CONSTRAINT quizzes_exercise_id_fkey
        FOREIGN KEY (exercise_id) REFERENCES exercises (id)
            ON DELETE RESTRICT ON UPDATE CASCADE;

--  Exercises-Lessons
ALTER TABLE exercises
    DROP CONSTRAINT exercises_lesson_id_fkey;

ALTER TABLE exercises
    ADD CONSTRAINT exercises_lesson_id_fkey
        FOREIGN KEY (lesson_id) REFERENCES lessons (id)
            ON DELETE RESTRICT ON UPDATE CASCADE;

COMMIT;


