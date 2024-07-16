package api.challenge.thortful.application.dto;

import api.challenge.thortful.domain.model.GenderType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CharacterDTO {
    private final Long id;
    private final Long apiId;
    private final String uuid;
    private final String name;
    private final String height;
    private final String birthYear;
    private final GenderType gender;
    private final String homeworld;
}
