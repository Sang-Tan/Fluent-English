DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM chapters__enemies) THEN
            INSERT INTO chapters__enemies (chapter_num, enemy_id, damage, exp_gain)
            VALUES  (1, 1, 5, 10),
                    (1, 2, 8, 15),
                    (1, 3, 9, 17),
                    (2, 1, 15, 20),
                    (2, 2, 18, 30),
                    (2, 3, 25, 35),
                    (2, 4, 50, 100),
                    (3, 1, 60, 70),
                    (3, 2, 70, 80),
                    (3, 3, 80, 90),
                    (3, 4, 150, 200),
                    (4, 5, 1000, 1000),
                    (4, 6, 5000, 5000);
        END IF;
    END;
$$;