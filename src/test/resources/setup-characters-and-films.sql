INSERT INTO character (uuid, name, height, gender, homeworld, api_id) VALUES ('4b90a37e-e408-4c00-b373-5b0f961c00fc', 'Luke Skywalker', '172', 'MALE', 'Tatooine', 1);

INSERT INTO film (uuid, title, episode_id, director, producer, release_date) VALUES ('2d820190-bb7c-423e-9e42-ac93ef9ae94f', 'A New Hope', 4, 'George Lucas', 'Gary Kurtz, Rick McCallum', '1977-05-25');

INSERT INTO character_film (character_id, film_id) VALUES ((SELECT id FROM character WHERE uuid = '4b90a37e-e408-4c00-b373-5b0f961c00fc'), (SELECT id FROM film WHERE uuid = '2d820190-bb7c-423e-9e42-ac93ef9ae94f'));