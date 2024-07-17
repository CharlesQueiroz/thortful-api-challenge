package api.challenge.thortful.application.ports.out;

import api.challenge.thortful.application.dto.SwapiCharacterDTO;
import api.challenge.thortful.application.dto.SwapiCharacterResponseDTO;
import api.challenge.thortful.application.dto.SwapiFilmDTO;
import api.challenge.thortful.application.dto.SwapiStarshipDTO;
import io.vavr.collection.List;
import io.vavr.control.Option;

public interface StarWarsApiPort {

    Option<SwapiCharacterDTO> fetchCharacterById(Long apiId);

    List<SwapiFilmDTO> fetchFilmsByUrls(List<String> urls);

    List<SwapiStarshipDTO> fetchStarshipsByUrls(List<String> urls);

    Option<SwapiCharacterResponseDTO> fetchCharactersPaginated(int page, int size);
}
