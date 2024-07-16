package api.challenge.thortful.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record SwapiCharacterDTO(
        Long id,
        String name,
        String height,
        @JsonProperty("birth_year")
        String birthYear,
        String gender,
        String homeworld,
        List<String> films,
        List<String> starships
) {
}