ALTER TABLE users
ADD CONSTRAINT fk__users__level FOREIGN KEY (level) REFERENCES user_levels(level);