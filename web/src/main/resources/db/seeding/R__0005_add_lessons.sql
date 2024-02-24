DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM lessons) THEN
            INSERT INTO lessons (id, name, difficulty)
            VALUES (1, 'Large Mammals', 1),
                   (2, 'Cannies', 2),
                   (3, 'Felines', 3),
                   (4, 'Body Shape', 1),
                   (5, 'Body Weight', 2),
                   (6, 'Describing Appearance', 3),
                   (7, 'Nervous System', 3),
                   (8, 'Immune System', 4),
                   (9, 'The Head', 2),
                   (10, 'International Clothes', 3),
                   (11, 'Verbs Relating to Clothing', 3),
                   (12, 'Words Related to Music', 3),
                   (13, 'Making an Argument 1', 2),
                   (14, 'Making an Argument 2', 4),
                   (15, 'Making a Decision', 3),
                   (16, 'Making a Suggestion', 4),
                   (17, 'Giving an Advice', 2),
                   (18, 'Obligation and Rules', 5);
        END IF;
    END;
$$;