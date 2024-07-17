package api.challenge.thortful.application.ports.in;

import api.challenge.thortful.application.dto.CharacterDTO;
import api.challenge.thortful.application.dto.PaginatedResponse;

public interface GetCharactersByPageUseCase {

    PaginatedResponse<CharacterDTO> execute(int page, int size);
}
