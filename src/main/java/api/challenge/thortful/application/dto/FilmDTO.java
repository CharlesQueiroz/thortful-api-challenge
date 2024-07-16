package api.challenge.thortful.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "DTO representing a Star Wars film")
public record FilmDTO(
        @Schema(description = "Title of the film", example = "A New Hope")
        String title,

        @Schema(description = "Episode ID of the film", example = "4")
        Integer episodeId,

        @Schema(description = "Director of the film", example = "George Lucas")
        String director,

        @Schema(description = "Producer of the film", example = "Gary Kurtz, Rick McCallum")
        String producer,

        @Schema(description = "Release date of the film", example = "1977-05-25")
        LocalDate releaseDate
) {
}
