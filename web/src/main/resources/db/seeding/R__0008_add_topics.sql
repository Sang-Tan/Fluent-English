DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM topics) THEN
            INSERT INTO topics (id, name)
            VALUES (1, 'Animals'),
                   (2, 'Appearance'),
                   (3, 'Body'),
                   (4, 'Colors and Shapes'),
                   (5, 'Clothes and Fashion'),
                   (6, 'Linguistics'),
                   (7, 'Arts and Crafts'),
                   (8, 'Cinema and Theater'),
                   (9, 'Literature'),
                   (10, 'Music'),
                   (11, 'Media and Communication'),
                   (12, 'Foods and Drinks'),
                   (13, 'Agreement and Disagreement'),
                   (14, 'Opinion and Argument'),
                   (15, 'Certainly and Doubt'),
                   (16, 'Decision, Suggestion and Obligation'),
                   (17, 'Health and Sickness'),
                   (18, 'Medical and Science'),
                   (19, 'Architecture and Construction'),
                   (20, 'Games'),
                   (21, 'Home and Garden'),
                   (22, 'Personal Care'),
                   (23, 'Food Ingradients'),
                   (24, 'Food and Drink Preparation'),
                   (25, 'Eating, Drinking and Serving'),
                   (26, 'Performing Arts');
        END IF;
    END;
$$;