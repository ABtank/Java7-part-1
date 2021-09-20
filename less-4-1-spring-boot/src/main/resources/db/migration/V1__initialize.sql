DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE roles
(
    id   serial PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    id         serial PRIMARY KEY,
    login      varchar(30) NOT NULL UNIQUE,
    password   varchar(80) NOT NULL,
    email      varchar(50) NOT NULL UNIQUE,
    creator_id bigint      NOT NULL,
    dt_create  timestamp   NOT NULL DEFAULT NOW(),
    dt_modify  timestamp   NOT NULL DEFAULT NOW(),
    FOREIGN KEY (creator_id) references users (id)
);

DROP TABLE IF EXISTS users_roles CASCADE;
CREATE TABLE users_roles
(
    user_id bigint NOT NULL,
    role_id int    NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) references users (id),
    FOREIGN KEY (role_id) references roles (id)
);

INSERT INTO users (login, password, email, creator_id)
VALUES ('abtank', '$2a$10$B3/978SYdNajS2bSrEX/W./aP.jz1YkZ42DPDcL94.wpmkAPAbGjK', 'abtank@bk.ru', 1)
;

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (1, 2);

DROP TABLE IF EXISTS categories CASCADE;
-- Категории упражнений (спина, ноги, КАРДИО .....)
CREATE TABLE categories
(
    category_id serial PRIMARY KEY,
    category    varchar(50) NOT NULL,
    descr       text,
    creator_id  bigint      NOT NULL,
    dt_create   timestamp   NOT NULL DEFAULT NOW(),
    FOREIGN KEY (creator_id) references users (id),
    UNIQUE (category, creator_id)
);

INSERT INTO categories (category, descr, creator_id)
VALUES ('Бицепсы', '', 1),
       ('Грудь', '', 1),
       ('Кардио', '', 1),
       ('Ноги', '', 1),
       ('Плечи', '', 1),
       ('Предплечья', '', 1),
       ('Пресс', '', 1),
       ('Спина', '', 1),
       ('Трицепсы', '', 1);

DROP TABLE IF EXISTS modes CASCADE;
-- тип упражнения в тренировке (Регулягное, Альтернативное, Суперсет(начало), Суперсет, Дополнительное)
CREATE TABLE modes
(
    mode_id  serial PRIMARY KEY,
    mode     varchar(50) NOT NULL,
    is_start boolean     NOT NULL,
    descr    text,
    UNIQUE (mode)
);

INSERT INTO modes (mode, is_start, descr)
VALUES ('Регулярное', true, ''),
       ('Альтернативное', false, ''),
       ('Суперсет (начало)', true, ''),
       ('Суперсет', false, ''),
       ('Дополнительное', true, '');

DROP TABLE IF EXISTS characters CASCADE;
-- Характер упражнения
CREATE TABLE characters
(
    character_id serial PRIMARY KEY,
    character    varchar(80) NOT NULL,
    descr        text
);

INSERT INTO characters (character, descr)
VALUES ('Глобальный', 'Работает больше 2/3 мышечных групп'),
       ('Региональный', 'Работает от 1/3 до 2/3 мышечных групп'),
       ('Локальный', 'Работает менее 1/3 мышечных групп');

DROP TABLE IF EXISTS exercises CASCADE;
-- упражнение
CREATE TABLE exercises
(
    exercise_id   serial PRIMARY KEY,
    exercise      varchar(80) NOT NULL,
    descr         text,
    category_id   int         NOT NULL,
    character_id  int         NOT NULL,
    is_cardio     boolean     NOT NULL DEFAULT false,
    cardio_name_1 varchar(10),
    cardio_name_2 varchar(10),
    cardio_name_3 varchar(10),
    creator_id    bigint      NOT NULL,
    dt_create     timestamp   NOT NULL DEFAULT NOW(),
    FOREIGN KEY (creator_id) references users (id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id),
    FOREIGN KEY (character_id) REFERENCES characters (character_id)
);

INSERT INTO exercises (exercise, category_id, character_id, is_cardio, creator_id)
VALUES ('попеременное сгибание предплечий с гантелями с супинацией', 1, 2, false, 1),
       ('попеременное сгибание рук с гантелями хват молот', 1, 3, false, 1),
       ('концентрирование сгибание одной руки с гантелью', 1, 3, false, 1),
       ('сгибание предплечий с нижнего блока хватом "молот"', 1, 3, false, 1),
       ('сгибание одной руки с рукояткой нижнего блока', 1, 3, false, 1),
       ('сгибание рук с рукоятками верхних блоков', 1, 3, false, 1),
       ('сгибание с грифом штанги', 1, 2, false, 1),
       ('Сгибание с грифом штанги пронированным хватом', 1, 2, false, 1),
       ('сгибание рук на скамье "LARRY-SCOTT"', 1, 3, false, 1),
       ('Сгибание рук со штангой хватом сверху', 1, 3, false, 1),
       ('Сгибание запястий со штангой хватом снизу', 6, 3, false, 1),
       ('Разгибания запястий со штангой хватом сверху', 6, 2, false, 1),
       ('разгибание рук с рукояткой верхнего блока хватом снизу', 9, 2, false, 1),
       ('разгибание одной руки с верхним блоком хватом снизу', 9, 3, false, 1),
       ('Французсо штангой лежа', 9, 1, false, 1),
       ('разгибание рук с гантелями лежа', 9, 1, false, 1),
       ('разгибание одной руки с гантелью из-за головы', 9, 2, false, 1),
       ('разгибание рук с одной гантелью из-за головы', 9, 2, false, 1),
       ('разгибание рук с изогнутым грифом штанги из-за головы', 9, 2, false, 1),
       ('разгибание одной руки назад с гантелью в наклоне', 9, 2, false, 1),
       ('отжимание трицепсами спиной к скамье', 9, 1, false, 1),
       ('отжимания на брусьях на трицепс', 9, 1, false, 1),
       ('жим штанги узким хватом, лежа на скамье', 9, 1, false, 1),
       ('жим штанги из-за головы сидя', 5, 1, false, 1),
       ('жим штанги с груди сидя узкий хват', 5, 1, false, 1),
       ('жим штанги с груди сидя широкий хват', 5, 1, false, 1),
       ('жим гантелей сидя', 5, 1, false, 1),
       ('попеременный жим гантелей с поворотами запястий', 5, 2, false, 1),
       ('подъемы гантелей в стороны в наклоне вперед', 5, 2, false, 1),
       ('подъемы гантелей в стороны', 5, 2, false, 1),
       ('подъемы гантелей вперед попеременно', 5, 2, false, 1),
       ('подъем гантели в сторону одной рукой, лежа на боку', 5, 2, false, 1),
       ('подъем одной руки в сторону нижнего блока', 5, 2, false, 1),
       ('подъем одной руки вперед с нижнего блока стоя', 5, 2, false, 1),
       ('перекрестные махи руками назад с верхних блоков', 5, 2, false, 1),
       ('перекресные махи руками назад с нижних блоков в наклоне', 5, 3, false, 1),
       ('подъемы рук вперед с одной гантелью', 5, 3, false, 1),
       ('подъемы штанги вперед', 5, 3, false, 1),
       ('плечевая передняя протяжка', 5, 1, false, 1),
       ('подъемы рук в стороны на тренажере', 5, 2, false, 1),
       ('махи руками назад с рукоятками тренажера', 5, 3, false, 1),
       ('жим штанги, лежа на наклонной скамье', 2, 1, false, 1),
       ('жим штанги, лежа на горизонтальной скамье', 2, 1, false, 1),
       ('жим штанги узким хватом, лежа на скамье', 2, 1, false, 1),
       ('жим штанги, лежа на скамье с уклоном', 2, 1, false, 1),
       ('отжимания от пола', 2, 1, false, 1),
       ('отжимания на брусьях', 2, 1, false, 1),
       ('жим гантелей лежа', 2, 1, false, 1),
       ('разведение гантелей лежа', 2, 2, false, 1),
       ('жим гантелей, лежа на наклонной скамье', 2, 1, false, 1),
       ('сведение рук на тренажере', 2, 2, false, 1),
       ('сведение верхних блоков "CROSS-OVER"', 2, 2, false, 1),
       ('Тяга гантели из-за головы лежа "PULL-OVER"', 2, 2, false, 1),
       ('Тяга штанги лежа "PULL-OVER"', 2, 2, false, 1),
       ('подтягивание на перекладине хватом снизу', 8, 1, false, 1),
       ('подтягивание на специальной перекладине', 8, 1, false, 1),
       ('тяги верхнего блока перед собой', 8, 1, false, 1),
       ('тяги верхнего блока за шею', 8, 1, false, 1),
       ('тяги верхнего блока узким хватом', 8, 1, false, 1),
       ('тяги верхнего блока прямыми руками', 8, 1, false, 1),
       ('тяги нижнего блока (гребля)', 8, 1, false, 1),
       ('тяги гантели одной рукой', 8, 1, false, 1),
       ('тяги штанги, стоя в наклоне', 8, 1, false, 1),
       ('тяги Т-образного грифа (гребля)', 8, 1, false, 1),
       ('тяги Т-образного грифа (гребля) с упором', 8, 1, false, 1),
       ('"мертвые" тяги со штангой, ноги прямые', 8, 1, false, 1),
       ('"мертвые" тяги в стиле Сумо', 8, 1, false, 1),
       ('Становые тяги со штангой', 8, 1, false, 1),
       ('Гиперэкстензия ', 8, 2, false, 1),
       ('разгибание туловища на ртенажере', 8, 2, false, 1),
       ('вертикальные тяги', 8, 2, false, 1),
       ('шраги со штангой ', 8, 3, false, 1),
       ('шраги с гантелями', 8, 3, false, 1),
       ('шраги на тренажере', 8, 3, false, 1),
       ('приседания с гантелями', 4, 1, false, 1),
       ('приседания со штангой на груди', 4, 1, false, 1),
       ('приседания со штангой на плечах', 4, 1, false, 1),
       ('широкие приседания', 4, 1, false, 1),
       ('нклонный жим ногами', 4, 1, false, 1),
       ('приседания на тренажере "HACK SQUAT"', 4, 1, false, 1),
       ('разгибание ног', 4, 2, false, 1),
       ('сгибание ног лежа', 4, 2, false, 1),
       ('сгибание одной ноги стоя', 4, 2, false, 1),
       ('сгибания ног сидя', 4, 2, false, 1),
       ('подъемы торса "с добрым утром"', 4, 2, false, 1),
       ('приведение одной ноги стоя', 4, 2, false, 1),
       ('сведение ног сидя', 4, 2, false, 1),
       ('подъемы на носки стоя', 4, 3, false, 1),
       ('подъем на носок одной ноги стоя', 4, 3, false, 1),
       ('подъемы на носки в наклоне "ослик"', 4, 3, false, 1),
       ('разгибание голени сидя', 4, 3, false, 1),
       ('разгибание голени сидя, со штангой на коленях', 4, 3, false, 1),
       ('выпады со штангой на плечах', 4, 1, false, 1),
       ('выпады с гантелями', 4, 1, false, 1),
       ('махи ногой назад с нижнего блока', 4, 3, false, 1),
       ('махи ногой назад с рычагом тренажера', 4, 2, false, 1),
       ('махи ногой назад на полу', 4, 3, false, 1),
       ('"мостик" лежа', 4, 1, false, 1),
       ('махи ногой в сторону с нижнего блока', 4, 3, false, 1),
       ('махи ногой в сторону с рычагом тренажера', 4, 3, false, 1),
       ('махи ногой в сторону, лежа на боку', 4, 3, false, 1),
       ('разведение ног на тренажере', 4, 2, false, 1),
       ('сворачивание туловища на полу', 7, 3, false, 1),
       ('подьемы туловища', 7, 2, false, 1),
       ('подъемы туловища у гимнастической стенки', 7, 2, false, 1),
       ('сворачивание туловища с голенью на скамье', 7, 3, false, 1),
       ('подьемы туловища на наклонной скамье', 7, 3, false, 1),
       ('подьемы туловища на вертиальной скамье', 7, 2, false, 1),
       ('сворачивание туловища с верхним блоком', 7, 3, false, 1),
       ('сворачивание туловища на тренажере', 7, 3, false, 1),
       ('подъемы ног на наклонной скамье', 7, 3, false, 1),
       ('подъемы коленей в упоре', 7, 3, false, 1),
       ('подьемы коленей в висе', 7, 3, false, 1),
       ('развороты туловища стоя', 7, 3, false, 1),
       ('боковые наклоны туловища стоя', 7, 3, false, 1),
       ('боковые подъемы туловища на римском стуле', 7, 3, false, 1),
       ('вращение туловища стоя на тренажере "твист"', 7, 3, false, 1);


DROP TABLE IF EXISTS workouts CASCADE;
-- Тренировка
CREATE TABLE workouts
(
    workout_id serial PRIMARY KEY,
    workout    varchar(80) NOT NULL UNIQUE,
    descr      text,
    creator_id bigint      NOT NULL,
    dt_create  timestamp   NOT NULL DEFAULT NOW(),
    FOREIGN KEY (creator_id) references users (id)
);

INSERT INTO workouts (workout, descr, creator_id)
VALUES ('Test workout', 'descr', 1);

DROP TABLE IF EXISTS workouts_exercises CASCADE;
-- упражнения в тренеровке (комплекс)
CREATE TABLE workouts_exercises
(
    workout_id  bigint    NOT NULL,
    exercise_id int       NOT NULL,
    mode_id     int       NOT NULL DEFAULT 1,
    ordinal     int       NOT NULL,
    descr       text,
    creator_id  bigint    NOT NULL,
    dt_create   timestamp NOT NULL DEFAULT NOW(),
    PRIMARY KEY (workout_id, exercise_id),
    FOREIGN KEY (creator_id) references users (id),
    FOREIGN KEY (workout_id) references workouts (workout_id),
    FOREIGN KEY (exercise_id) references exercises (exercise_id),
    FOREIGN KEY (mode_id) references modes (mode_id)
);

INSERT INTO workouts_exercises (workout_id, exercise_id, ordinal, creator_id)
VALUES (1, 1, 1, 1),
       (1, 20, 2, 1),
       (1, 30, 3, 1),
       (1, 40, 4, 1),
       (1, 50, 5, 1);

DROP TABLE IF EXISTS sets CASCADE;
-- подход
CREATE TABLE sets
(
    set_id      bigserial PRIMARY KEY,
    workout_id  bigint NOT NULL,
    exercise_id bigint NOT NULL,
    creator_id  bigint NOT NULL,
    dt_session  timestamp DEFAULT NOW(),
    weight      varchar(10),
    reps        varchar(10),
    descr       text,
    cardio_1    varchar(10),
    cardio_2    varchar(10),
    cardio_3    varchar(10),
    dt_create   timestamp DEFAULT NOW(),
    FOREIGN KEY (creator_id) references users (id),
    FOREIGN KEY (workout_id) references workouts (workout_id),
    FOREIGN KEY (exercise_id) references exercises (exercise_id)
);

