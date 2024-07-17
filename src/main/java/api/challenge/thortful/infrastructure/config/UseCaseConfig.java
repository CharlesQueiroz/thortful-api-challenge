package api.challenge.thortful.infrastructure.config;

import api.challenge.thortful.application.dto.FilmDTO;
import api.challenge.thortful.application.dto.StarshipDTO;
import api.challenge.thortful.application.dto.SwapiFilmDTO;
import api.challenge.thortful.application.dto.SwapiStarshipDTO;
import api.challenge.thortful.application.mapper.FilmMapper;
import api.challenge.thortful.application.mapper.StarshipMapper;
import api.challenge.thortful.application.ports.in.FetchEntitiesUseCase;
import api.challenge.thortful.application.ports.in.GetEntitiesByCharacterUuidUseCase;
import api.challenge.thortful.application.ports.out.StarWarsApiPort;
import api.challenge.thortful.application.usercase.FetchEntitiesUseCaseFactory;
import api.challenge.thortful.application.usercase.GetEntitiesByCharacterUuidUseCaseFactory;
import api.challenge.thortful.domain.model.FilmEntity;
import api.challenge.thortful.domain.model.StarshipEntity;
import api.challenge.thortful.domain.service.CharacterService;
import io.vavr.collection.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public FetchEntitiesUseCase<FilmEntity, SwapiFilmDTO> fetchFilmsUseCase(
            StarWarsApiPort starWarsApiPort,
            FilmMapper filmMapper,
            FetchEntitiesUseCaseFactory factory) {

        return factory.create(starWarsApiPort::fetchFilmsByUrls, filmMapper::swapiDtoToEntity);
    }

    @Bean
    public FetchEntitiesUseCase<StarshipEntity, SwapiStarshipDTO> fetchStarshipsUseCase(
            StarWarsApiPort starWarsApiPort,
            StarshipMapper starshipMapper,
            FetchEntitiesUseCaseFactory factory) {
        return factory.create(starWarsApiPort::fetchStarshipsByUrls, starshipMapper::swapiDtoToEntity);
    }

    @Bean
    public GetEntitiesByCharacterUuidUseCase<FilmDTO> getFilmsByCharacterUuidUseCase(
            CharacterService characterService,
            FilmMapper filmMapper,
            GetEntitiesByCharacterUuidUseCaseFactory factory) {
        return factory.create(
                characterService,
                filmMapper::entityToDto,
                character -> List.ofAll(character.getFilms())
        );
    }

    @Bean
    public GetEntitiesByCharacterUuidUseCase<StarshipDTO> getStarshipsByCharacterUuidUseCase(
            CharacterService characterService,
            StarshipMapper starshipMapper,
            GetEntitiesByCharacterUuidUseCaseFactory factory) {
        return factory.create(
                characterService,
                starshipMapper::entityToDto,
                character -> List.ofAll(character.getStarships())
        );
    }
}