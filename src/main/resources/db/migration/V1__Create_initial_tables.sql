CREATE TABLE character
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid       VARCHAR(36)  NOT NULL UNIQUE,
    name       VARCHAR(255) NOT NULL,
    height     VARCHAR(10),
    birth_year VARCHAR(10),
    gender     VARCHAR(20),
    homeworld  VARCHAR(255)
);

CREATE TABLE film
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid         VARCHAR(36)  NOT NULL UNIQUE,
    title        VARCHAR(255) NOT NULL,
    episode_id   INT,
    director     VARCHAR(255),
    producer     VARCHAR(255),
    release_date DATE
);

CREATE TABLE starship
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid            VARCHAR(36)  NOT NULL UNIQUE,
    name            VARCHAR(255) NOT NULL,
    model           VARCHAR(255),
    cost_in_credits VARCHAR(50),
    length          VARCHAR(50),
    passengers      VARCHAR(50),
    cargo_capacity  VARCHAR(50),
    starship_class  VARCHAR(100)
);

CREATE TABLE character_film
(
    character_id BIGINT,
    film_id      BIGINT,
    PRIMARY KEY (character_id, film_id),
    FOREIGN KEY (character_id) REFERENCES character (id),
    FOREIGN KEY (film_id) REFERENCES film (id)
);

CREATE TABLE character_starship
(
    character_id BIGINT,
    starship_id  BIGINT,
    PRIMARY KEY (character_id, starship_id),
    FOREIGN KEY (character_id) REFERENCES character (id),
    FOREIGN KEY (starship_id) REFERENCES starship (id)
);

CREATE TABLE film_starship
(
    film_id     BIGINT,
    starship_id BIGINT,
    PRIMARY KEY (film_id, starship_id),
    FOREIGN KEY (film_id) REFERENCES film (id),
    FOREIGN KEY (starship_id) REFERENCES starship (id)
);