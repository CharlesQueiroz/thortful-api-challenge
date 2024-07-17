package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.ports.in.GetEntitiesByCharacterUuidUseCase;
import api.challenge.thortful.domain.model.CharacterEntity;
import api.challenge.thortful.domain.service.CharacterService;
import io.vavr.collection.List;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Factory class for creating instances of {@link GetEntitiesByCharacterUuidUseCase}.
 *
 * <p>This class is responsible for creating instances of {@link GetEntitiesByCharacterUuidUseCase}
 * with the necessary dependencies. It uses generics to allow for flexible creation of use cases
 * that can handle different types of entities associated with a character.</p>
 *
 * <p>The factory utilizes the {@link CharacterService} to interact with the character data,
 * a mapper function to convert entities to DTOs, and a getter function to retrieve the specific entities
 * from a {@link CharacterEntity}.</p>
 */
@Component
public class GetEntitiesByCharacterUuidUseCaseFactory {

    /**
     * Creates an instance of {@link GetEntitiesByCharacterUuidUseCase}.
     *
     * <p>This method initializes a new instance of {@link GetEntitiesByCharacterUuidUseCaseImpl}
     * with the provided {@link CharacterService}, mapper function, and entity getter function.</p>
     *
     * @param characterService The service to interact with character data.
     * @param mapper           The function to map entities to DTOs.
     * @param entityGetter     The function to retrieve specific entities from a {@link CharacterEntity}.
     * @param <T>              The type of the DTO.
     * @param <E>              The type of the entity.
     * @return A new instance of {@link GetEntitiesByCharacterUuidUseCaseImpl}.
     */
    public <T, E> GetEntitiesByCharacterUuidUseCase<T> create(
            CharacterService characterService,
            Function<E, T> mapper,
            Function<CharacterEntity, List<E>> entityGetter) {
        return new GetEntitiesByCharacterUuidUseCaseImpl<>(mapper, characterService, entityGetter);
    }
}