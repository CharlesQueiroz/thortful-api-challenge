-- Remove associations
DELETE FROM character_starship;

-- Remove data
DELETE FROM starship;
DELETE FROM character;

-- Reset auto-increment
ALTER TABLE starship ALTER COLUMN id RESTART WITH 1;
ALTER TABLE character ALTER COLUMN id RESTART WITH 1;