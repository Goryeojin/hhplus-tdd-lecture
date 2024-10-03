-- Lecture
INSERT INTO lecture (id, lecture_title, lecturer, lecture_date_time) VALUES ('1', 'lecturer1', '허재코치', '2024-10-04 08:00:00');
INSERT INTO lecture (id, lecture_title, lecturer, lecture_date_time) VALUES ('2', 'lecturer2', '헌우코치', '2024-10-04 10:00:00');
INSERT INTO lecture (id, lecture_title, lecturer, lecture_date_time) VALUES ('3', 'lecturer3', '타일러코치', '2024-10-04 18:00:00');
INSERT INTO lecture (id, lecture_title, lecturer, lecture_date_time) VALUES ('4', 'lecturer4', '려진코치', '2024-10-04 22:00:00');
INSERT INTO lecture (id, lecture_title, lecturer, lecture_date_time) VALUES ('5', 'lecturer5', '현우코치', '2024-10-04 23:00:00');
INSERT INTO lecture (id, lecture_title, lecturer, lecture_date_time) VALUES ('6', 'lecturer6', '허재코치', '2024-10-11 08:00:00');
INSERT INTO lecture (id, lecture_title, lecturer, lecture_date_time) VALUES ('7', 'lecturer7', '헌우코치', '2024-10-11 10:00:00');
INSERT INTO lecture (id, lecture_title, lecturer, lecture_date_time) VALUES ('8', 'lecturer8', '타일러코치', '2024-10-11 18:00:00');
INSERT INTO lecture (id, lecture_title, lecturer, lecture_date_time) VALUES ('9', 'lecturer9', '려진코치', '2024-10-11 22:00:00');
INSERT INTO lecture (id, lecture_title, lecturer, lecture_date_time) VALUES ('10', 'lecturer10', '현우코치', '2024-10-11 23:00:00');

-- LectureCapacity
INSERT INTO lecture_capacity (id, lecture_id, capacity, current_count) VALUES ('1', '1', '30', '0');
INSERT INTO lecture_capacity (id, lecture_id, capacity, current_count) VALUES ('2', '2', '30', '0');
INSERT INTO lecture_capacity (id, lecture_id, capacity, current_count) VALUES ('3', '3', '30', '0');
INSERT INTO lecture_capacity (id, lecture_id, capacity, current_count) VALUES ('4', '4', '30', '0');
INSERT INTO lecture_capacity (id, lecture_id, capacity, current_count) VALUES ('5', '5', '30', '29');
INSERT INTO lecture_capacity (id, lecture_id, capacity, current_count) VALUES ('6', '6', '30', '0');
INSERT INTO lecture_capacity (id, lecture_id, capacity, current_count) VALUES ('7', '7', '30', '0');
INSERT INTO lecture_capacity (id, lecture_id, capacity, current_count) VALUES ('8', '8', '30', '0');
INSERT INTO lecture_capacity (id, lecture_id, capacity, current_count) VALUES ('9', '9', '30', '0');
INSERT INTO lecture_capacity (id, lecture_id, capacity, current_count) VALUES ('10', '10', '30', '29');
