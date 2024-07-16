package api.challenge.thortful.infrastructure.adapters.out.persistence;

import api.challenge.thortful.domain.model.CharacterEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends BaseRepository<CharacterEntity> {
}
