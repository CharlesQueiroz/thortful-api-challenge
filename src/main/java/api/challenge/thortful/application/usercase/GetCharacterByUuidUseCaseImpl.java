package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.dto.CharacterDTO;
import api.challenge.thortful.application.mapper.CharacterMapper;
import api.challenge.thortful.application.ports.in.GetCharacterByUuidUseCase;
import api.challenge.thortful.application.ports.out.StarWarsApiPort;
import api.challenge.thortful.domain.model.CharacterEntity;
import api.challenge.thortful.domain.service.CharacterService;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetCharacterByUuidUseCaseImpl implements GetCharacterByUuidUseCase {

    private final CharacterService characterService;
    private final CharacterMapper characterMapper;
    private final StarWarsApiPort starWarsApiPort;

    @Override
    public Option<CharacterDTO> execute(Long apiId) {
        return characterService.findByApiId(apiId)
                .peek(character -> log.info("FOUND EXISTING CHARACTER WITH ID: {}", apiId))
                .orElse(() -> fetchFromApiAndSave(apiId))
                .map(characterMapper::entityToDto);
    }

    private Option<CharacterEntity> fetchFromApiAndSave(Long apiId) {
        return starWarsApiPort.fetchCharacterById(apiId)
                .flatMap(swapiDto -> Option.of(characterMapper.swapiDtoToEntity(swapiDto)))
                .map(character -> {
                    character.setApiId(apiId);
                    return characterService.create(character);
                })
                .peek(character -> {
                    if (character == null) {
                        throw new RuntimeException("Failed to create character");
                    }
                });
    }
}
