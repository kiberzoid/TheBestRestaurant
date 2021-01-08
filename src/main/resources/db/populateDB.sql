DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM votes;
DELETE FROM restaurants;
DELETE FROM users;
ALTER TABLE meals ALTER COLUMN id RESTART WITH 1;
ALTER TABLE votes ALTER COLUMN id RESTART WITH 1;
ALTER TABLE restaurants ALTER COLUMN id RESTART WITH 1;
ALTER TABLE users ALTER COLUMN id RESTART WITH 1;

INSERT INTO users (id, name, email, password)
VALUES (1, 'User1', 'user1@mail.ru', '{noop}user1'),
       (2, 'User2', 'user2@mail.ru', '{noop}user2'),
       (3, 'Admin1', 'admin1@mail.ru', '{noop}admin1');

insert into user_roles(user_id, role)
values (1, 'USER'),
       (2, 'USER'),
       (3, 'ADMIN');

INSERT INTO restaurants (id, name, address)
VALUES (1, 'Dodici', 'Rozhdestvenskaya 1'),
       (2, 'Vitalich', 'Pokrovskay 12'),
       (3, 'Praha', 'Pokrovskay 145');

INSERT INTO meals (restaurant_id, name, price, meal_date)
VALUES (1, 'Fish Solyanka', 11.99, CURRENT_DATE - 3),
       (1, 'Chicken Salad', 4.99, CURRENT_DATE - 3),

       (2, 'Risotto', 12.99, CURRENT_DATE - 3),
       (2, 'Cheese Salad', 6.99, CURRENT_DATE - 3),

       (3, 'Carpaccio Of Beef', 22, CURRENT_DATE - 3),
       (3, 'Crab Salad', 23, CURRENT_DATE - 3),
       (3, 'Cranberry Juice', 3, CURRENT_DATE - 3),


       (1, 'Gnocchi With Goat Cheese', 16, CURRENT_DATE - 2),
       (1, 'Chicken Salad', 4.99, CURRENT_DATE - 2),
       (1, 'Black Currant Juice', 2.99, CURRENT_DATE - 2),

       (2, 'Smoked Beef Ribs', 49.99, CURRENT_DATE - 2),
       (2, 'Cherries Kisel', 4.99, CURRENT_DATE - 2),

       (3, 'Rib-eye', 33.99, CURRENT_DATE - 2),
       (3, 'Rabbit Cutlet', 10, CURRENT_DATE - 2),


       (1, 'Russian Borsch', 7.99, CURRENT_DATE - 1),
       (1, 'Flank Steak', 22.99, CURRENT_DATE - 1),

       (2, 'Parmesan Chicken', 16.49, CURRENT_DATE - 1),
       (2, 'Caesar salad', 9.99, CURRENT_DATE - 1),

       (3, 'Minestrone', 11.99, CURRENT_DATE - 1),
       (3, 'Crab Salad', 23, CURRENT_DATE - 1),


       (1, 'Green Salad', 4.99, CURRENT_DATE),
       (1, 'Ginger Chicken', 22.99, CURRENT_DATE),

       (2, 'Filet Of Beef', 49.99, CURRENT_DATE),
       (2, 'Quesadilla With Chicken', 5.99, CURRENT_DATE);

INSERT INTO votes (restaurant_id, user_id, date_vote, restaurant_name, user_email)
VALUES (1, 1, CURRENT_DATE - 3,'Dodici', 'user1@mail.ru'),
       (2, 2, CURRENT_DATE - 3,'Vitalich', 'user2@mail.ru'),

       (3, 1, CURRENT_DATE - 2,'Praha', 'user1@mail.ru'),
       (1, 2, CURRENT_DATE - 2,'Dodici', 'user2@mail.ru'),

       (1, 1, CURRENT_DATE - 1,'Dodici', 'user1@mail.ru'),
       (2, 2, CURRENT_DATE - 1,'Vitalich', 'user2@mail.ru');