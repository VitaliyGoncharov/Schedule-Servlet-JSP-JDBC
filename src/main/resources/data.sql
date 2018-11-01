# '#' - this is only one comment sign
# One query per line is required!!!

# (`id`, `title`, `url`)
INSERT INTO school VALUES (1, 'Школа экономики', 'school_of_economics_and_management')
INSERT INTO school VALUES (2, 'Юридическая школа', 'law_school')
INSERT INTO school VALUES (3, 'Школа естественных наук', 'school_of_natural_sciences')

# (`id`, `title`, `url`, `duration`, `school`)
INSERT INTO major VALUES (1, 'Economics Security', 'economics_security', 5, 1)
INSERT INTO major VALUES (2, 'Economics', 'economics', 4, 1)
INSERT INTO major VALUES (3, 'Management', 'management', 4, 1)

# (`id`, `title`, `course_num`, `major`)
INSERT INTO groups VALUES (1, 'C1401пд', 4, 1)
INSERT INTO groups VALUES (2, 'C1401пе', 4, 1)
INSERT INTO groups VALUES (3, 'C1401пв', 4, 1)

# (`id`, `name`, `order_num`)
INSERT INTO day VALUES (1, 'Понедельник', 1)
INSERT INTO day VALUES (2, 'Вторник', 2)
INSERT INTO day VALUES (3, 'Среда', 3)
INSERT INTO day VALUES (4, 'Четверг', 4)
INSERT INTO day VALUES (5, 'Пятница', 5)
INSERT INTO day VALUES (6, 'Суббота', 6)

# (`id`, `title`)
INSERT INTO subject VALUES (1, 'Управление затратами на предприятии')
INSERT INTO subject VALUES (2, 'Ценообразование')
INSERT INTO subject VALUES (3, 'Планирование на предприятии')
INSERT INTO subject VALUES (4, 'Информационная безопасность')
INSERT INTO subject VALUES (5, 'Экономическая безопасность')
INSERT INTO subject VALUES (6, 'Уголовный процесс')

# (`id`, `firstname`, `lastname`, `middlename`, `mail`) 
INSERT INTO teacher VALUES (1, 'Полина','Ефремова','Витальевна','efremova.pv@dvfu.ru')
INSERT INTO teacher VALUES (2, 'Лариса','Воробьева','Генадьевна','vorobeva.lg@dvfu.ru')
INSERT INTO teacher VALUES (3, 'Лидия','Мишунина','Николаевна','mishunina.ln@dvfu.ru')
INSERT INTO teacher VALUES (4, 'Ольга','Карпец','Викторовна','karpets.ov@dvfu.ru')

# (`id`, `subject`, `day_num`, `week`, `subject_num`, `major`, `group_id`, `teacher`, `lesson_type`, `classroom`)
INSERT INTO schedule VALUES (1, 4, 1, 'down', 5, 1, 1, NULL, 'Л', 'G427')
INSERT INTO schedule VALUES (2, 6, 1, 'down', 6, 1, 1, NULL, 'Л', 'G207')

INSERT INTO schedule VALUES (3, 2, 2, 'up', 3, 1, 1, 4, 'П', 'G502')
INSERT INTO schedule VALUES (4, 2, 2, 'down', 3, 1, 1, 4, 'П', 'G502')
INSERT INTO schedule VALUES (5, 3, 2, 'up', 4, 1, 1, 3, 'П', 'G420')
INSERT INTO schedule VALUES (6, 3, 2, 'down', 4, 1, 1, 3, 'П', 'G420')
INSERT INTO schedule VALUES (7, 6, 2, 'up', 5, 1, 1, NULL, 'П', 'G420')
INSERT INTO schedule VALUES (8, 4, 2, 'down', 5, 1, 1, NULL, 'П', 'G241')
INSERT INTO schedule VALUES (9, 1, 2, 'up', 6, 1, 1, 2, 'Л', 'G427')
INSERT INTO schedule VALUES (10, 1, 2, 'down', 6, 1, 1, 2, 'Л', 'G427')

INSERT INTO schedule VALUES (11, 5, 4, 'up', 5, 1, 1, NULL, 'Л', 'G211')
INSERT INTO schedule VALUES (12, 5, 4, 'down', 5, 1, 1, NULL, 'Л', 'G211')
INSERT INTO schedule VALUES (13, 5, 4, 'up', 6, 1, 1, 1, 'П', 'G501')
INSERT INTO schedule VALUES (14, 5, 4, 'down', 6, 1, 1, 1, 'П', 'G501')

INSERT INTO schedule VALUES (15, 1, 5, 'up', 5, 1, 1, 2, 'П', 'G231')
INSERT INTO schedule VALUES (16, 1, 5, 'down', 5, 1, 1, 2, 'П', 'G231')
INSERT INTO schedule VALUES (17, 2, 5, 'up', 6, 1, 1, 4, 'Л', 'G427')
INSERT INTO schedule VALUES (18, 3, 5, 'down', 6, 1, 1, 3, 'Л', 'G427')