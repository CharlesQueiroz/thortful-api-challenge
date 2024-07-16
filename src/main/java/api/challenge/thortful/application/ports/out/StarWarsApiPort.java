package api.challenge.thortful.application.ports.out;

import api.challenge.thortful.application.dto.SwapiCharacterDTO;
import api.challenge.thortful.application.dto.SwapiFilmDTO;
import io.vavr.control.Option;

import io.vavr.collection.List;

public interface StarWarsApiPort {

    Option<SwapiCharacterDTO> fetchCharacterById(Long apiId);
    List<SwapiFilmDTO> fetchFilmsByUrls(List<String> urls);
}
