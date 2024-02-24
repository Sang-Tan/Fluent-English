DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM enemies) THEN
            INSERT INTO enemies (id, name)
            VALUES  (1, 'Trùm trường'),
                    (2, 'Đệ trùm trường'),
                    (3, 'Cường giả cùng lớp'),
                    (4, 'Thủ khoa đạo nhân'),
                    (5, 'Bạn trai crush'),
                    (6, 'Ba vợ');
        END IF;
    END;
$$;