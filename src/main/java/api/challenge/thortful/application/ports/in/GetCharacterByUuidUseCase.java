package api.challenge.thortful.application.ports.in;

import api.challenge.thortful.application.dto.CharacterDTO;

public interface GetCharacterByUuidUseCase {

    CharacterDTO execute(Long apiId);
}
