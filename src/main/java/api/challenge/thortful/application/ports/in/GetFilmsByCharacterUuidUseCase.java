package api.challenge.thortful.application.ports.in;

import api.challenge.thortful.application.dto.FilmDTO;
import io.vavr.collection.List;

public interface GetFilmsByCharacterUuidUseCase {

    List<FilmDTO> execute(String characterUuid);
}
