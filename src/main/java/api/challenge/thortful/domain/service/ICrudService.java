package api.challenge.thortful.domain.service;

import io.vavr.control.Option;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.stream.Stream;

@NoRepositoryBean
public interface ICrudService<T> {

    Option<T> find(Long id);

    Option<T> findByUuid(String uuid);

    Stream<T> findAll();

    T create(T entity);

    T update(Long id, T entity);

    void delete(Long id);
}
