package api.challenge.thortful.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "film")
public class FilmEntity extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(name = "episode_id")
    private Integer episodeId;

    private String director;

    private String producer;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "films")
    private List<CharacterEntity> characters;

    @ManyToMany
    @JoinTable(
            name = "film_starship",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "starship_id")
    )
    @JsonIgnore
    @ToString.Exclude
    private List<StarshipEntity> starships;
}