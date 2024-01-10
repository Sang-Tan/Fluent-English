CREATE TABLE IF NOT EXISTS quizzes
(
    id SERIAL PRIMARY KEY,

    exercise_id INTEGER NOT NULL
        REFERENCES exercises (id) ON DELETE CASCADE,

    position INTEGER NOT NULL,

    question JSONB NOT NULL
        CONSTRAINT question_answers__question__is_object
        CHECK (jsonb_typeof(question) = 'object'),


    answer JSONB NOT NULL
        CONSTRAINT question_answers__answer__is_object
        CHECK (jsonb_typeof(answer) = 'object'),

    UNIQUE (exercise_id, position) DEFERRABLE INITIALLY DEFERRED
);