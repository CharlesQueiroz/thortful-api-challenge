package api.challenge.thortful.domain.service;

import api.challenge.thortful.domain.model.BaseEntity;
import api.challenge.thortful.infrastructure.adapters.out.persistence.BaseRepository;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
public abstract class CrudService<T extends BaseEntity, R extends BaseRepository<T>> implements ICrudService<T> {

    protected final R repository;

    @Override
    public Option<T> find(Long id) {
        return Option.ofOptional(repository.findById(id));
    }

    public Option<T> findByUuid(String uuid) {
        return repository.findByUuid(uuid);
    }

    public Option<T> findByApiId(Long apiId) {
        return repository.findByApiId(apiId);
    }

    @Override
    public Stream<T> findAll() {
        return repository.findAll().stream();
    }

    @Override
    @Transactional
    public T create(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public T update(Long id, T entity) {
        return repository.findById(id)
                .map(existingEntity -> {
                    entity.setId(id);
                    return repository.save(entity);
                }).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
