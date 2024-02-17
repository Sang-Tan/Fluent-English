CREATE TABLE users__ignored_words
(
    user_id INT NOT NULL,
    word_id INT NOT NULL,
    CONSTRAINT pk__users__ignored_words PRIMARY KEY (user_id, word_id),
    CONSTRAINT fk__users__ignored_words__user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk__users__ignored_words__word_id FOREIGN KEY (word_id) REFERENCES words(id)
);