package api.challenge.thortful.infrastructure.adapters.out.persistence;

import api.challenge.thortful.domain.model.CharacterEntity;
import io.vavr.control.Option;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends BaseRepository<CharacterEntity> {

    Option<CharacterEntity> findByApiId(Long apiId);
}
