package api.challenge.thortful.domain.service;

import api.challenge.thortful.domain.model.CharacterEntity;
import api.challenge.thortful.domain.repository.CharacterRepository;
import io.vavr.control.Option;
import org.springframework.stereotype.Service;

@Service
public class CharacterService extends CrudService<CharacterEntity, CharacterRepository> {

    public CharacterService(CharacterRepository repository) {
        super(repository);
    }

    public Option<CharacterEntity> findByApiId(Long apiId) {
        return repository.findByApiId(apiId);
    }
}
