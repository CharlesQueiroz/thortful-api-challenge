package api.challenge.thortful.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static jakarta.persistence.EnumType.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "character")
public class Character extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String height;

    @Column(name = "birth_year")
    private String birthYear;

    @Enumerated(STRING)
    private GenderType gender;

    private String homeworld;

    @ManyToMany
    @JoinTable(
            name = "character_film",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private List<Film> films;

    @ManyToMany
    @JoinTable(
            name = "character_starship",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "starship_id")
    )
    private List<Starship> starships;
}
