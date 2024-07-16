package api.challenge.thortful.application.mapper;

import api.challenge.thortful.application.dto.CharacterDTO;
import api.challenge.thortful.application.dto.SwapiCharacterDTO;
import api.challenge.thortful.domain.model.CharacterEntity;
import api.challenge.thortful.domain.model.GenderType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = GenderType.class)
public interface CharacterMapper {

    CharacterDTO entityToDto(CharacterEntity character);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "films", ignore = true)
    @Mapping(target = "starships", ignore = true)
    @Mapping(target = "apiId", source = "id")
    @Mapping(target = "gender", expression = "java(GenderType.fromString(swapiCharacter.gender()))")
    CharacterEntity swapiDtoToEntity(SwapiCharacterDTO swapiCharacter);
}
