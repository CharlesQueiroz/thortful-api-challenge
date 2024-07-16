-- Add api_id column to character table
ALTER TABLE character ADD COLUMN api_id BIGINT;

-- Add a unique constraint to api_id in character table
ALTER TABLE character ADD CONSTRAINT uk_character_api_id UNIQUE (api_id);

-- Add api_id column to film table
ALTER TABLE film ADD COLUMN api_id BIGINT;

-- Add a unique constraint to api_id in film table
ALTER TABLE film ADD CONSTRAINT uk_film_api_id UNIQUE (api_id);

-- Add api_id column to starship table
ALTER TABLE starship ADD COLUMN api_id BIGINT;

-- Add a unique constraint to api_id in starship table
ALTER TABLE starship ADD CONSTRAINT uk_starship_api_id UNIQUE (api_id);