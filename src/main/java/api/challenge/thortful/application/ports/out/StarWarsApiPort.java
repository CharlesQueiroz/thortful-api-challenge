package api.challenge.thortful.application.ports.out;

import api.challenge.thortful.application.dto.SwapiCharacterDTO;
import io.vavr.control.Option;

public interface StarWarsApiPort {

    Option<SwapiCharacterDTO> fetchCharacterById(Long apiId);
}
