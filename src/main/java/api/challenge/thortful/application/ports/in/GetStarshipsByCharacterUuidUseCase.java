package api.challenge.thortful.application.ports.in;

import api.challenge.thortful.application.dto.StarshipDTO;
import io.vavr.collection.List;

public interface GetStarshipsByCharacterUuidUseCase {

    List<StarshipDTO> execute(String uuid);
}