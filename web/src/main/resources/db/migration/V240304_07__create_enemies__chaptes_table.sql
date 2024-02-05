CREATE TABLE chapters__enemies
(
    chapter_num INTEGER,
    enemy_id    INTEGER,
    damage      INTEGER,
    exp_gain    INTEGER,
    CONSTRAINT pk__enemies__chapters PRIMARY KEY (chapter_num, enemy_id),
    CONSTRAINT fk__enemies__chapters__chapters FOREIGN KEY (chapter_num) REFERENCES chapters (number),
    CONSTRAINT fk__enemies__chapters__enemies FOREIGN KEY (enemy_id) REFERENCES enemies (id)
);