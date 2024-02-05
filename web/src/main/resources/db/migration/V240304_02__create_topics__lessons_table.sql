CREATE TABLE topics__lessons
(
    topic_id  INT NOT NULL,
    lesson_id INT NOT NULL,
    CONSTRAINT pk__topics__lessons PRIMARY KEY (topic_id, lesson_id),
    CONSTRAINT fk__topics__lessons__topic_id FOREIGN KEY (topic_id) REFERENCES topics (id),
    CONSTRAINT fk__topics__lessons__lesson_id FOREIGN KEY (lesson_id) REFERENCES lessons (id)
);