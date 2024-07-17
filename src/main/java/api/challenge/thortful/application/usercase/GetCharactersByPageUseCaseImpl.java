package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.dto.CharacterDTO;
import api.challenge.thortful.application.dto.PaginatedResponse;
import api.challenge.thortful.application.mapper.CharacterMapper;
import api.challenge.thortful.application.ports.in.GetCharactersByPageUseCase;
import api.challenge.thortful.domain.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCharactersByPageUseCaseImpl implements GetCharactersByPageUseCase {

    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

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