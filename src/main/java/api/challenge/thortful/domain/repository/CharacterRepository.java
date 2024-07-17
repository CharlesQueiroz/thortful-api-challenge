package api.challenge.thortful.domain.repository;

import api.challenge.thortful.domain.model.CharacterEntity;
import api.challenge.thortful.infrastructure.adapters.out.persistence.BaseRepository;
import io.vavr.control.Option;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends BaseRepository<CharacterEntity> {

    Option<CharacterEntity> findByApiId(Long apiId);
}
