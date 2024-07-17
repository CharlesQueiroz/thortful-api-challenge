package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.dto.SwapiCharacterDTO;
import api.challenge.thortful.application.dto.SwapiCharacterResponseDTO;
import api.challenge.thortful.application.mapper.CharacterMapper;
import api.challenge.thortful.application.ports.in.UpdateCharactersUseCase;
import api.challenge.thortful.application.ports.out.StarWarsApiPort;
import api.challenge.thortful.domain.service.CharacterService;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link UpdateCharactersUseCase} that updates characters by fetching data from the Star Wars API.
 *
 * <p>This class fetches paginated character data from an external Star Wars API and updates the local database
 * with new characters. If a character already exists, it is not updated.</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateCharactersUseCaseImpl implements UpdateCharactersUseCase {

    private final CharacterMapper characterMapper;
    private final StarWarsApiPort starWarsApiPort;
    private final CharacterService characterService;

    /**
     * Executes the use case to update characters.
     *
     * <p>This method fetches characters from the Star Wars API in a paginated manner. For each page, it iterates
     * through the characters and creates new character entries in the local database if they do not already exist.</p>
     */
    @Override
    @Transactional
    public void execute() {
        var page = 1;
        Option<SwapiCharacterResponseDTO> charactersPage;

        do {
            charactersPage = starWarsApiPort.fetchCharactersPaginated(page, 10);
            charactersPage.peek(response -> response.results().forEach(this::createCharacter));
            page++;
        } while (charactersPage.isDefined() && charactersPage.get().next() != null);
    }

    private void createCharacter(SwapiCharacterDTO swapiCharacter) {
        final var apiId = CharacterMapper.extractIdFromUrl(swapiCharacter.url());
        characterService.findByApiId(apiId)
                .onEmpty(() -> {
                    var characterEntity = characterMapper.swapiDtoToEntity(swapiCharacter);
                    var savedEntity = characterService.create(characterEntity);
                    log.debug("CHARACTER SAVED: {}", savedEntity);
                });
    }
}
