package api.challenge.thortful.application.ports.in;

import io.vavr.collection.List;
import io.vavr.control.Option;

/**
 * Generic use case interface for fetching a list of entities based on provided URLs.
 *
 * @param <T> The type of entity to fetch.
 */
public interface FetchEntitiesUseCase<T, D> {

    /**
     * Executes the use case to fetch a list of entities.
     *
     * @param urls an optional list of URLs pointing to the entities to be fetched.
     * @return a list of fetched entities, or an empty list if no URLs are provided.
     */
    List<T> execute(Option<List<String>> urls);
}