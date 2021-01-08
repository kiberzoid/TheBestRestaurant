DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 10000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY AUTO_INCREMENT,
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id          INTEGER                           NOT NULL,
    role             VARCHAR                           NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id               INTEGER PRIMARY KEY AUTO_INCREMENT,
    name             VARCHAR                           NOT NULL,
    address          VARCHAR                           NOT NULL
);
CREATE UNIQUE INDEX restaurants_unique_name_idx ON restaurants (name, address);

CREATE TABLE meals
(
    id               INTEGER PRIMARY KEY AUTO_INCREMENT,
    restaurant_id    INTEGER                           NOT NULL,
    name             VARCHAR                           NOT NULL,
    price            DOUBLE                            NOT NULL,
    meal_date        DATE DEFAULT current_date()       NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX meals_unique_restaurant_name_idx ON meals (name, meal_date, restaurant_id);

CREATE TABLE votes(
    id               INTEGER PRIMARY KEY AUTO_INCREMENT,
    restaurant_id    INTEGER                           NOT NULL,
    user_id          INTEGER                           NOT NULL,
    date_vote        DATE DEFAULT current_date()       NOT NULL,
    restaurant_name  VARCHAR                           NOT NULL,
    user_email       VARCHAR                           NOT NULL,
    CONSTRAINT user_vote_idx UNIQUE (user_id, date_vote),
    FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY(restaurant_id) REFERENCES restaurants(id) ON DELETE SET NULL
);
CREATE UNIQUE INDEX votes_restaurant_voted_idx ON votes(date_vote, user_id, restaurant_id);
CREATE INDEX votes_user_email_idx ON votes(user_email);
CREATE INDEX votes_restaurant_name_idx ON votes(restaurant_name);