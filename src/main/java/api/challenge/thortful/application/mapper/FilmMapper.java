package api.challenge.thortful.application.mapper;

import api.challenge.thortful.application.dto.SwapiFilmDTO;
import api.challenge.thortful.domain.model.FilmEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "starships", ignore = true)
    @Mapping(target = "characters", ignore = true)
    FilmEntity swapiDtoToEntity(SwapiFilmDTO swapiFilm);
}