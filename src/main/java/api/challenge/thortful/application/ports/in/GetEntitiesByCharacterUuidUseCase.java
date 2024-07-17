package api.challenge.thortful.application.ports.in;

import io.vavr.collection.List;

public interface GetEntitiesByCharacterUuidUseCase<T> {

    List<T> execute(String uuid);
}
