package api.challenge.thortful.domain.service;

import api.challenge.thortful.domain.model.CharacterEntity;
import api.challenge.thortful.domain.repository.CharacterRepository;
import io.vavr.control.Option;
import org.springframework.stereotype.Service;

/**
 * Service class for managing {@link CharacterEntity} entities.
 * <p>
 * This service provides CRUD operations inherited from {@link CrudService}
 * and custom methods specific to {@link CharacterEntity}.
 * </p>
 */
@Service
public class CharacterService extends CrudService<CharacterEntity, CharacterRepository> {

    public CharacterService(CharacterRepository repository) {
        super(repository);
    }

    /**
     * Finds a {@link CharacterEntity} by its API ID.
     *
     * @param apiId the API ID of the character to find.
     * @return an {@link Option} containing the found {@link CharacterEntity}, or {@link Option#none()} if no character is found.
     */
    public Option<CharacterEntity> findByApiId(Long apiId) {
        return repository.findByApiId(apiId);
    }
}
