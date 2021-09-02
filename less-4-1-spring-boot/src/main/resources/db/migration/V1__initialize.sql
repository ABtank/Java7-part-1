DROP TABLE IF EXISTS roles  CASCADE;
CREATE TABLE roles
(
    id   serial PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE
);

DROP TABLE  IF EXISTS users CASCADE;
CREATE TABLE users
(
    id       serial PRIMARY KEY,
    login    varchar(30) NOT NULL UNIQUE,
    password varchar(80) NOT NULL,
    email    varchar(50) NOT NULL UNIQUE,
    dt_create timestamp NOT NULL DEFAULT NOW(),
    modify_date timestamp NOT NULL DEFAULT NOW()
);

DROP TABLE  IF EXISTS users_roles CASCADE;
CREATE TABLE users_roles
(
    user_id bigint NOT NULL,
    role_id int    NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) references users (id),
    FOREIGN KEY (role_id) references roles (id)
);

DROP TABLE  IF EXISTS categories CASCADE;
-- Категории упражнений (спина, ноги, КАРДИО .....)
CREATE TABLE categories
(
    category_id serial PRIMARY KEY,
    category varchar(50) NOT NULL,
    descr        text,
    creator_id bigint NOT NULL,
    dt_create timestamp NOT NULL DEFAULT NOW(),
    FOREIGN KEY (creator_id) references users (id),
    UNIQUE (category, creator_id)
);


DROP TABLE  IF EXISTS modes CASCADE;
 -- тип упражнения в тренировке (Регулягное, Альтернативное, Суперсет(начало), Суперсет, Дополнительное)
CREATE TABLE modes
(
    mode_id serial PRIMARY KEY,
    mode varchar(50) NOT NULL,
    is_start boolean NOT NULL,
    descr        text,
    creator_id bigint NOT NULL,
    FOREIGN KEY (creator_id) references users (id),
    UNIQUE (mode, creator_id)
);


DROP TABLE  IF EXISTS exercises CASCADE;
-- упражнение
CREATE TABLE exercises (
    exercise_id          serial  PRIMARY KEY,
    exercise    varchar(80)  NOT NULL,
    descr        text,
    category_id int NOT NULL,
    is_cardio   boolean NOT NULL,
    cardio_name_1 varchar(10),
    cardio_name_2 varchar(10),
    cardio_name_3 varchar(10),
    creator_id bigint NOT NULL,
    dt_create timestamp NOT NULL DEFAULT NOW(),
    FOREIGN KEY (creator_id) references users (id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

DROP TABLE  IF EXISTS workouts CASCADE;
-- Тренировка
CREATE TABLE workouts ( 
    workout_id         serial PRIMARY KEY ,
    workout       varchar(80)    NOT NULL UNIQUE,
    descr        text,
    creator_id bigint NOT NULL,
    dt_create timestamp NOT NULL DEFAULT NOW(),
    FOREIGN KEY (creator_id) references users (id)
);

DROP TABLE  IF EXISTS workouts_exercises CASCADE;
-- упражнения в тренеровке (комплекс)
CREATE TABLE workouts_exercises
(
    workout_id bigint NOT NULL,
    exercise_id bigint    NOT NULL,
    mode_id serial NOT NULL,
    ordinal int NOT NULL,
    descr        text,
    creator_id bigint NOT NULL,
    dt_create timestamp NOT NULL DEFAULT NOW(),
    PRIMARY KEY (workout_id, exercise_id),
    FOREIGN KEY (creator_id) references users (id),    
    FOREIGN KEY (workout_id) references workouts (workout_id),
    FOREIGN KEY (exercise_id) references exercises (exercise_id),
    FOREIGN KEY (mode_id) references modes (mode_id)
);

DROP TABLE  IF EXISTS sets CASCADE;
-- подход
CREATE TABLE sets (
    set_id      bigserial PRIMARY KEY,
    workout_id bigint NOT NULL,
    exercise_id bigint NOT NULL,
    creator_id bigint NOT NULL,
    dt_session timestamp DEFAULT NOW(),
    weight      varchar(10),
    reps        varchar(10),
    descr        text,
    cardio_1     varchar(10),
    cardio_2     varchar(10),
    cardio_3     varchar(10),
    dt_create  timestamp DEFAULT NOW(),
    FOREIGN KEY (creator_id) references users (id),    
    FOREIGN KEY (workout_id) references workouts (workout_id),
    FOREIGN KEY (exercise_id) references exercises (exercise_id)
);


INSERT INTO roles (name)
VALUES ('ROLE_USER'),('ROLE_ADMIN');
