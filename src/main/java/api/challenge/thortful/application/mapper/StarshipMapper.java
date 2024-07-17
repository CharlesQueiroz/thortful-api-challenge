package api.challenge.thortful.application.mapper;

import api.challenge.thortful.application.dto.StarshipDTO;
import api.challenge.thortful.application.dto.SwapiStarshipDTO;
import api.challenge.thortful.domain.model.StarshipEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StarshipMapper {

    StarshipDTO entityToDto(StarshipEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "films", ignore = true)
    @Mapping(target = "characters", ignore = true)
    StarshipEntity swapiDtoToEntity(SwapiStarshipDTO swapiStarship);
}