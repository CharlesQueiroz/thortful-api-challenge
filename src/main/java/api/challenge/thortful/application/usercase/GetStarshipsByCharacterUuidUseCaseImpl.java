package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.dto.StarshipDTO;
import api.challenge.thortful.application.mapper.StarshipMapper;
import api.challenge.thortful.application.ports.in.GetStarshipsByCharacterUuidUseCase;
import api.challenge.thortful.common.exception.ResourceNotFoundException;
import api.challenge.thortful.domain.service.CharacterService;
import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.vavr.collection.List.ofAll;

@Service
@RequiredArgsConstructor
public class GetStarshipsByCharacterUuidUseCaseImpl implements GetStarshipsByCharacterUuidUseCase {

    private final StarshipMapper starshipMapper;
    private final CharacterService characterService;

    @Override
    public List<StarshipDTO> execute(String uuid) {
        return characterService.findByUuid(uuid)
                .map(character -> ofAll(character.getStarships())
                        .map(starshipMapper::entityToDto))
                .getOrElseThrow(() -> new ResourceNotFoundException("CHARACTER NOT FOUND WITH UUID: " + uuid));
    }
}