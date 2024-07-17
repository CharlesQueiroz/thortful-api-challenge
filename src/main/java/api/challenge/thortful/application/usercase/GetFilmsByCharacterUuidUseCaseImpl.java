package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.dto.FilmDTO;
import api.challenge.thortful.application.mapper.FilmMapper;
import api.challenge.thortful.application.ports.in.GetFilmsByCharacterUuidUseCase;
import api.challenge.thortful.common.exception.ResourceNotFoundException;
import api.challenge.thortful.domain.service.CharacterService;
import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetFilmsByCharacterUuidUseCaseImpl implements GetFilmsByCharacterUuidUseCase {

    private final FilmMapper filmMapper;
    private final CharacterService characterService;

    @Override
    public List<FilmDTO> execute(String uuid) {
        return characterService.findByUuid(uuid)
                .map(character -> List.ofAll(character.getFilms())
                        .map(filmMapper::entityToDto))
                .getOrElseThrow(() -> new ResourceNotFoundException("CHARACTER NOT FOUND WITH UUID: " + uuid));
    }
}