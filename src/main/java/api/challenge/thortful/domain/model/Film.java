package api.challenge.thortful.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "film")
public class Film extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(name = "episode_id")
    private Integer episodeId;

    private String director;

    private String producer;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ManyToMany(mappedBy = "films")
    private List<Character> characters;

    @ManyToMany
    @JoinTable(
            name = "film_starship",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "starship_id")
    )
    private List<Starship> starships;
}