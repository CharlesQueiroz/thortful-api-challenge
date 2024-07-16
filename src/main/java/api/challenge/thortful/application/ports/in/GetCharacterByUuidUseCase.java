package api.challenge.thortful.application.ports.in;

import api.challenge.thortful.application.dto.CharacterDTO;
import io.vavr.control.Option;

public interface GetCharacterByUuidUseCase {

    Option<CharacterDTO> execute(Long apiId);
}
