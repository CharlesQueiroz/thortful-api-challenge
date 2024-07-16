package api.challenge.thortful.infrastructure.adapters.out.persistence;

import api.challenge.thortful.domain.model.BaseEntity;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

    Option<T> findByUuid(String uuid);

    Option<T> findByApiId(Long apiId);
}
