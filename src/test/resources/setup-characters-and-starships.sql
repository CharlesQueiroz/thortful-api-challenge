-- Insert a character
INSERT INTO character (id, uuid, api_id, name, height, birth_year, gender, homeworld) VALUES (1, '4b90a37e-e408-4c00-b373-5b0f961c00fc', 1, 'Luke Skywalker', '172', '19BBY', 'MALE', 'Tatooine');

-- Insert starships
INSERT INTO starship (id, uuid, name, model, cost_in_credits, length, passengers, cargo_capacity, starship_class) VALUES (1, '3e3a7447-05bf-4bb2-a062-9d4e0271cb3f',  'X-wing', 'T-65 X-wing', '149999', '12.5', '1', '110', 'Starfighter');
INSERT INTO starship (id, uuid, name, model, cost_in_credits, length, passengers, cargo_capacity, starship_class) VALUES (2, '28f3ac8b-8914-40d6-99db-96fd850fa6db', 'Imperial shuttle', 'Lambda-class T-4a shuttle', '240000', '20', '20', '80000', 'Armed government transport');

-- Associate character with starships
INSERT INTO character_starship (character_id, starship_id) VALUES (1, 1);
INSERT INTO character_starship (character_id, starship_id) VALUES (1, 2);