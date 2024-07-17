package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.dto.CharacterDTO;
import api.challenge.thortful.application.dto.PaginatedResponse;
import api.challenge.thortful.application.mapper.CharacterMapper;
import api.challenge.thortful.application.ports.in.GetCharactersByPageUseCase;
import api.challenge.thortful.domain.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link GetCharactersByPageUseCase} interface.
 * <p>
 * This class provides functionality to retrieve a paginated list of Star Wars characters.
 * It interacts with the CharacterRepository to fetch the data and maps the entities to DTOs.
 */
@Service
@RequiredArgsConstructor
public class GetCharactersByPageUseCaseImpl implements GetCharactersByPageUseCase {

    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    /**
     * Executes the use case to retrieve a paginated list of characters.
     * <p>
     * This method fetches a page of characters from the repository, maps the character entities to DTOs,
     * and returns a {@link PaginatedResponse} containing the character data along with pagination details.
     *
     * @param page The page number to retrieve.
     * @param size The size of the page to retrieve.
     * @return A {@link PaginatedResponse} containing the list of {@link CharacterDTO} and pagination details.
     */
    @Override
    public PaginatedResponse<CharacterDTO> execute(int page, int size) {
        var pageable = PageRequest.of(page, size);
        var characterPage = characterRepository.findAll(pageable);
        var characters = characterPage.getContent().stream()
                .map(characterMapper::entityToDto)
                .toList();

        return new PaginatedResponse<>(
                characters,
                characterPage.getNumber(),
                characterPage.getSize(),
                characterPage.getTotalElements()
        );
    }
}