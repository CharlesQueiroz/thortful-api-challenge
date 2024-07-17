package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.ports.in.FetchEntitiesUseCase;
import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

/**
 * Implementation of the {@link FetchEntitiesUseCase} interface.
 * <p>
 * This class provides functionality to fetch and transform entities based on the provided
 * fetch and mapping functions.
 *
 * @param <T> The type of the final transformed entity.
 * @param <D> The type of the entity fetched from the external source.
 */
@Slf4j
public class FetchEntitiesUseCaseImpl<T, D> implements FetchEntitiesUseCase<T, D> {

    private final Function<D, T> mapper;
    private final Function<List<String>, List<D>> fetchFunction;

    /**
     * Constructs an instance of {@link FetchEntitiesUseCaseImpl} with the specified fetch and mapping functions.
     *
     * @param fetchFunction The function to fetch entities from a list of URLs.
     * @param mapper        The function to transform the fetched entities to the desired type.
     */
    FetchEntitiesUseCaseImpl(Function<List<String>, List<D>> fetchFunction, Function<D, T> mapper) {
        this.mapper = mapper;
        this.fetchFunction = fetchFunction;
    }

    /**
     * Executes the fetch and transform operation.
     * <p>
     * This method takes an {@link Option} containing a list of URLs, fetches the corresponding entities using
     * the fetch function, transforms them using the mapping function, and returns the transformed entities.
     * If the provided Option is empty, an empty list is returned.
     *
     * @param urls An {@link Option} containing a list of URLs to fetch entities from.
     * @return A list of transformed entities.
     */
    @Override
    @Transactional
    public List<T> execute(Option<List<String>> urls) {
        return urls.map(fetchFunction)
                .map(list -> list.map(mapper))
                .getOrElse(List.empty());
    }
}