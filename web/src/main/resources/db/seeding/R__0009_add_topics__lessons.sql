DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM topics__lessons) THEN
            INSERT INTO topics__lessons (topic_id, lesson_id)
            VALUES (1, 1),
                   (1, 2),
                   (1, 3),
                   (2, 4),
                   (2, 5),
                   (2, 6),
                   (3, 7),
                   (3, 8),
                   (3, 9),
                   (5, 10),
                   (5, 11),
                   (10, 12),
                   (14, 13),
                   (14, 14),
                   (12, 15),
                   (12, 16),
                   (21, 17),
                   (21, 18);
        END IF;
    END;
$$;