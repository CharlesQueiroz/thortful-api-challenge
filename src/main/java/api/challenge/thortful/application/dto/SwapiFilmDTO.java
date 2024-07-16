package api.challenge.thortful.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SwapiFilmDTO(
        String title,
        @JsonProperty("episode_id")
        Integer episodeId,
        String director,
        String producer,
        @JsonProperty("release_date")
        LocalDate releaseDate
) {
}
