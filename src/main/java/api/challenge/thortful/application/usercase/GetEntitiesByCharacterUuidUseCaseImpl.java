package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.ports.in.GetEntitiesByCharacterUuidUseCase;
import api.challenge.thortful.common.exception.ResourceNotFoundException;
import api.challenge.thortful.domain.model.CharacterEntity;
import api.challenge.thortful.domain.service.CharacterService;
import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

/**
 * Implementation of {@link GetEntitiesByCharacterUuidUseCase} that retrieves specific entities associated with a character.
 *
 * <p>This class uses generics to handle different types of entities related to a character, such as films or starships.
 *
 * @param <T> The type of the DTO.
 * @param <E> The type of the entity.
 */
@RequiredArgsConstructor
public class GetEntitiesByCharacterUuidUseCaseImpl<T, E> implements GetEntitiesByCharacterUuidUseCase<T> {

    private final Function<E, T> mapper;
    private final CharacterService characterService;
    private final Function<CharacterEntity, List<E>> entityGetter;

    /**
     * Executes the use case to retrieve entities associated with a character by their UUID.
     *
     * <p>This method finds the character by UUID using the {@link CharacterService}, then retrieves the associated entities
     * and maps them to DTOs using the provided mapper function. If the character is not found, a {@link ResourceNotFoundException}
     * is thrown.</p>
     *
     * @param uuid The UUID of the character.
     * @return A list of mapped entities.
     * @throws ResourceNotFoundException if the character is not found.
     */
    @Override
    public List<T> execute(String uuid) {
        return characterService.findByUuid(uuid)
                .map(character -> entityGetter.apply(character).map(mapper))
                .getOrElseThrow(() -> new ResourceNotFoundException("CHARACTER NOT FOUND WITH UUID: " + uuid));
    }
}