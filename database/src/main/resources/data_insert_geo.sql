INSERT INTO country (id, is_deleted, title)
VALUES ('1', FALSE, 'Russia'), ('2', FALSE, 'USA'), ('3', FALSE, 'China');

INSERT INTO city (id, is_deleted, title, country_id)
VALUES ('1', FALSE, 'Moscow', '1'), ('2', FALSE, 'Kazan', '1'),
('3', FALSE, 'Detroit', '2');