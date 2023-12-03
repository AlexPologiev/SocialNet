INSERT INTO country (id, is_deleted, title)
VALUES ('1', FALSE, 'Russia'), ('2', FALSE, 'USA'), ('3', FALSE, 'China'),
('4', FALSE, 'Japan'), ('5', FALSE, 'South Korea'), ('6', FALSE, 'Australia');

------------------

INSERT INTO city (id, is_deleted, title, country_id)
VALUES ('1', FALSE, 'Moscow', '1'), ('2', FALSE, 'Kazan', '1'),
('3', FALSE, 'Detroit', '2'), ('4', FALSE, 'Tokyo', '4'),
('5', FALSE, 'Sydney', '6'), ('6', FALSE, 'Seoul', '5');
