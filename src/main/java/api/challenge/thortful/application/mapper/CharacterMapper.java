package api.challenge.thortful.application.mapper;

import api.challenge.thortful.application.dto.CharacterDTO;
import api.challenge.thortful.application.dto.SwapiCharacterDTO;
import api.challenge.thortful.domain.model.CharacterEntity;
import api.challenge.thortful.domain.model.GenderType;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", imports = GenderType.class)
public interface CharacterMapper {

    CharacterDTO entityToDto(CharacterEntity character);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "films", ignore = true)
    @Mapping(target = "starships", ignore = true)
    @Mapping(target = "apiId", source = "url", qualifiedByName = "extractIdFromUrl")
    @Mapping(target = "gender", expression = "java(GenderType.fromString(swapiCharacter.gender()))")
    CharacterEntity swapiDtoToEntity(SwapiCharacterDTO swapiCharacter);

    /**
     * Utility method to extract the numeric ID from the last part of a URL.
     * <p>
     * This method uses the Vavr library to handle null and empty values, and
     * attempts to parse the last segment of the URL as a Long. If the URL is null,
     * empty, or the last segment is not a valid number, it returns null.
     * </p>
     *
     * @param url the URL from which to extract the ID. Can be null or empty.
     * @return the extracted ID as a Long, or null if the URL is null, empty, or
     *         the last segment is not a valid number.
     */
    @Named("extractIdFromUrl")
    static Long extractIdFromUrl(String url) {
        return Option.of(url)
                .filter(u -> !u.isEmpty())
                .map(u -> u.split("/"))
                .flatMap(parts -> Try.of(() -> Long.parseLong(parts[parts.length - 1])).toOption())
                .getOrNull();
    }
}
