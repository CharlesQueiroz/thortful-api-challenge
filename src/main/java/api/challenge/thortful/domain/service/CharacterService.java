package api.challenge.thortful.domain.service;

import api.challenge.thortful.domain.model.CharacterEntity;
import api.challenge.thortful.infrastructure.adapters.out.persistence.CharacterRepository;
import org.springframework.stereotype.Service;

@Service
public class CharacterService extends CrudService<CharacterEntity, CharacterRepository> {

    public CharacterService(CharacterRepository repository) {
        super(repository);
    }
}
