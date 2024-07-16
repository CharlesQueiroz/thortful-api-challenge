package api.challenge.thortful.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SwapiCharacterDTO {
    private Long id;
    private String name;
    private String height;
    @JsonProperty("birth_year")
    private String birthYear;
    private String gender;
    private String homeworld;
    private List<String> films;
    private List<String> starships;
    private LocalDateTime created;
    private LocalDateTime edited;
}
