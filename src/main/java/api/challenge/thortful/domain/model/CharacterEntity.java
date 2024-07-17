package api.challenge.thortful.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;

import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static org.hibernate.annotations.CascadeType.PERSIST;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Table(name = "character")
public class CharacterEntity extends BaseEntity {

    @Column(nullable = false, unique = true, name = "api_id")
    private Long apiId;

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
    @Cascade(PERSIST)
    @ToString.Exclude
    private List<FilmEntity> films;

    @ManyToMany
    @JoinTable(
            name = "character_starship",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "starship_id")
    )
    @Cascade(PERSIST)
    @ToString.Exclude
    private List<StarshipEntity> starships;
}
