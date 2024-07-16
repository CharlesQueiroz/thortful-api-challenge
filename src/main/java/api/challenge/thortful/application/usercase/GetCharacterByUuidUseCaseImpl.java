package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.dto.CharacterDTO;
import api.challenge.thortful.application.mapper.CharacterMapper;
import api.challenge.thortful.application.ports.in.FetchFilmsUseCase;
import api.challenge.thortful.application.ports.in.GetCharacterByUuidUseCase;
import api.challenge.thortful.application.ports.out.StarWarsApiPort;
import api.challenge.thortful.domain.model.CharacterEntity;
import api.challenge.thortful.domain.service.CharacterService;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetCharacterByUuidUseCaseImpl implements GetCharacterByUuidUseCase {

    private final CharacterService characterService;
    private final CharacterMapper characterMapper;
    private final StarWarsApiPort starWarsApiPort;
    private final FetchFilmsUseCase fetchFilmsUseCase;

    @Override
    @Transactional
    public Option<CharacterDTO> execute(Long apiId) {
        return characterService.findByApiId(apiId)
                .peek(character -> log.info("FOUND EXISTING CHARACTER WITH ID: {}", apiId))
                .orElse(() -> fetchAndSaveCharacterWithDetails(apiId))
                .map(characterMapper::entityToDto);
    }

    private Option<CharacterEntity> fetchAndSaveCharacterWithDetails(Long apiId) {
        return starWarsApiPort.fetchCharacterById(apiId)
                .map(swapiDto -> {
                    var character = characterMapper.swapiDtoToEntity(swapiDto);
                    character.setApiId(apiId);

                    var films = fetchFilmsUseCase.execute(swapiDto.films());
                    character.setFilms(films.toJavaList());

                    return characterService.create(character);
                });
    }
}
