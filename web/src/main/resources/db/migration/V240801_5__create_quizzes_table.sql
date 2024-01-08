CREATE TABLE IF NOT EXISTS quizzes
(
    id SERIAL PRIMARY KEY,

    lesson_id INTEGER NOT NULL
        REFERENCES lessons (id) ON DELETE CASCADE,

    position INTEGER NOT NULL,

    question JSONB NOT NULL
        CONSTRAINT question_answers__question__is_object
        CHECK (jsonb_typeof(question) = 'object'),


    answer JSONB NOT NULL
        CONSTRAINT question_answers__answer__is_object
        CHECK (jsonb_typeof(answer) = 'object'),

    UNIQUE (lesson_id, position) DEFERRABLE INITIALLY DEFERRED
);