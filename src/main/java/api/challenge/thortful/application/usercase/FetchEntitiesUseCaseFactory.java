package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.ports.in.FetchEntitiesUseCase;
import io.vavr.collection.List;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Factory class for creating instances of {@link FetchEntitiesUseCase}.
 * <p>
 * This factory provides a method to create instances of FetchEntitiesUseCase with specified
 * fetch and mapping functions. The created instances can be used to fetch and transform
 * entities based on the provided functions.
 */
@Component
public class FetchEntitiesUseCaseFactory {

    /**
     * Creates an instance of {@link FetchEntitiesUseCase} with the specified fetch and mapping functions.
     *
     * @param <T>           The type of the final transformed entity.
     * @param <D>           The type of the entity fetched from the external source.
     * @param fetchFunction The function to fetch entities from a list of URLs.
     * @param mapper        The function to transform the fetched entities to the desired type.
     * @return An instance of FetchEntitiesUseCase that uses the provided fetch and mapping functions.
     */
    public <T, D> FetchEntitiesUseCase<T, D> create(Function<List<String>, List<D>> fetchFunction, Function<D, T> mapper) {
        return new FetchEntitiesUseCaseImpl<>(fetchFunction, mapper);
    }
}