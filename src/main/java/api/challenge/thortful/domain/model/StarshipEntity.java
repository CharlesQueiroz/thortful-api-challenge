package api.challenge.thortful.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "starship")
public class StarshipEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String model;

    @Column(name = "cost_in_credits")
    private String costInCredits;

    private String length;

    private String passengers;

    @Column(name = "cargo_capacity")
    private String cargoCapacity;

    @Column(name = "starship_class")
    private String starshipClass;

    @ManyToMany(mappedBy = "starships")
    private List<CharacterEntity> characters;

    @ManyToMany(mappedBy = "starships")
    private List<FilmEntity> films;
}
