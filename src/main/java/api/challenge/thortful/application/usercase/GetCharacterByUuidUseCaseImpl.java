package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.dto.CharacterDTO;
import api.challenge.thortful.application.dto.SwapiFilmDTO;
import api.challenge.thortful.application.dto.SwapiStarshipDTO;
import api.challenge.thortful.application.mapper.CharacterMapper;
import api.challenge.thortful.application.ports.in.FetchEntitiesUseCase;
import api.challenge.thortful.application.ports.in.GetCharacterByUuidUseCase;
import api.challenge.thortful.application.ports.out.StarWarsApiPort;
import api.challenge.thortful.domain.model.CharacterEntity;
import api.challenge.thortful.domain.model.FilmEntity;
import api.challenge.thortful.domain.model.GenderType;
import api.challenge.thortful.domain.model.StarshipEntity;
import api.challenge.thortful.domain.service.CharacterService;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link GetCharacterByUuidUseCase} interface.
 * <p>
 * This class provides functionality to retrieve a Star Wars character by its API ID, fetching details
 * from an external source if the character is not found in the local database.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetCharacterByUuidUseCaseImpl implements GetCharacterByUuidUseCase {

    private final CharacterService characterService;
    private final CharacterMapper characterMapper;
    private final StarWarsApiPort starWarsApiPort;
    private final FetchEntitiesUseCase<FilmEntity, SwapiFilmDTO> fetchFilmsUseCase;
    private final FetchEntitiesUseCase<StarshipEntity, SwapiStarshipDTO> fetchStarshipsUseCase;

    /**
     * Executes the use case to retrieve a character by its API ID.
     * <p>
     * This method first attempts to find the character in the local database. If the character is not found,
     * it fetches the character and its related details (films and starships) from an external API, saves the
     * character in the local database, and returns the character details as a {@link CharacterDTO}.
     *
     * @param apiId The API ID of the character.
     * @return An {@link Option} containing the {@link CharacterDTO} if found, or an empty {@link Option} if not found.
     */
    @Override
    @Transactional
    public Option<CharacterDTO> execute(Long apiId) {
        return characterService.findByApiId(apiId)
                .orElse(() -> fetchAndSaveCharacterWithDetails(apiId))
                .map(characterMapper::entityToDto);
    }

    private Option<CharacterEntity> fetchAndSaveCharacterWithDetails(Long apiId) {
        return starWarsApiPort.fetchCharacterById(apiId)
                .map(swapiDto -> {
                    var films = fetchFilmsUseCase.execute(Option.of(swapiDto.films())).toJavaList();
                    var starships = fetchStarshipsUseCase.execute(Option.of(swapiDto.starships())).toJavaList();

                    return CharacterEntity.builder()
                            .apiId(apiId)
                            .name(swapiDto.name())
                            .height(swapiDto.height())
                            .gender(GenderType.fromString(swapiDto.gender()))
                            .homeworld(swapiDto.homeworld())
                            .films(films)
                            .starships(starships)
                            .build();

                })
                .map(characterService::create);
    }
}
