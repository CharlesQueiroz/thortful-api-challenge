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

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateCharactersUseCaseImpl implements UpdateCharactersUseCase {

    private final CharacterMapper characterMapper;
    private final StarWarsApiPort starWarsApiPort;
    private final CharacterService characterService;

    @Override
    @Transactional
    public void execute() {
        int page = 1;
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
                    log.debug("Character saved: {}", savedEntity);
                });
    }
}
