DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM user_levels) THEN
            INSERT INTO user_levels (level, exp_to_next_level, base_hp, base_shield)
            VALUES  (1, 100, 10, 5),
                    (2, 200, 20, 10),
                    (3, 300, 30, 15),
                    (4, 400, 40, 20),
                    (5, 500, 50, 25),
                    (6, 600, 60, 30),
                    (7, 700, 70, 35),
                    (8, 800, 80, 40),
                    (9, 900, 90, 45),
                    (10, 1000, 100, 50);
        END IF;
    END;
$$;