package api.challenge.thortful.domain.service;

import api.challenge.thortful.domain.model.FilmEntity;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@DataJpaTest
@ActiveProfiles("test")
@Import(FilmService.class)
class FilmServiceTest {

    @Autowired
    private FilmService filmService;

    @Test
    @Sql("/setup-films.sql")
    @Sql(scripts = "/cleanup-films.sql", executionPhase = AFTER_TEST_METHOD)
    void givenFilmExists_whenFindById_thenFilmIsReturned() {
        assertEquals("A New Hope", filmService.find(2L).get().getTitle());
    }

    @Test
    void givenFilmDoesNotExist_whenFindById_thenEmptyOptionIsReturned() {
        var result = filmService.find(999L);
        assertEquals(Option.none(), result);
    }

    @Test
    @Sql("/setup-films.sql")
    @Sql(scripts = "/cleanup-films.sql", executionPhase = AFTER_TEST_METHOD)
    void givenFilmExists_whenFindByUuid_thenFilmIsReturned() {
        var uuid = "4b90a37e-e408-4c00-b373-5b0f961c00fc";
        assertEquals("A New Hope", filmService.findByUuid(uuid).get().getTitle());
    }

    @Test
    void givenFilmDoesNotExist_whenFindByUuid_thenEmptyOptionIsReturned() {
        var uuid = UUID.randomUUID().toString();
        var result = filmService.findByUuid(uuid);
        assertEquals(Option.none(), result);
    }

    @Test
    @Sql("/setup-films.sql")
    @Sql(scripts = "/cleanup-films.sql", executionPhase = AFTER_TEST_METHOD)
    void givenFilmsExist_whenFindAll_thenAllFilmsAreReturned() {
        var result = filmService.findAll();
        assertEquals(1, result.toList().size());
    }

    @Test
    void givenNoFilmsExist_whenFindAll_thenEmptyStreamIsReturned() {
        var result = filmService.findAll();
        assertEquals(0, result.toList().size());
    }

    @Test
    void givenNewFilm_whenCreate_thenFilmIsCreated() {
        var filmEntity = FilmEntity.builder()
                .title("Revenge of the Sith")
                .episodeId(3)
                .director("George Lucas")
                .producer("Rick McCallum")
                .releaseDate(LocalDate.of(2005, 5, 19))
                .build();

        var result = filmService.create(filmEntity);

        assertNotNull(result.getId());
        assertEquals(filmEntity.getTitle(), result.getTitle());
    }

    @Test
    void givenNewFilmWithoutTitle_whenCreate_thenExceptionIsThrown() {
        var filmEntity = FilmEntity.builder()
                .episodeId(3)
                .director("George Lucas")
                .producer("Rick McCallum")
                .releaseDate(LocalDate.of(2005, 5, 19))
                .build();

        assertThrows(Exception.class, () -> filmService.create(filmEntity));
    }

    @Test
    @Sql("/setup-films.sql")
    @Sql(scripts = "/cleanup-films.sql", executionPhase = AFTER_TEST_METHOD)
    void givenExistingFilm_whenUpdate_thenFilmIsUpdated() {
        var savedEntity = filmService.findByTitle("A New Hope").get();
        savedEntity.setTitle("Updated Title");
        var updatedEntity = filmService.update(savedEntity.getId(), savedEntity);

        assertEquals("Updated Title", updatedEntity.getTitle());
    }

    @Test
    void givenNonExistentFilm_whenUpdate_thenEmptyOptionIsReturned() {
        var filmEntity = FilmEntity.builder()
                .title("Non-Existent Film")
                .episodeId(3)
                .director("Unknown Director")
                .producer("Unknown Producer")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .build();
        var result = filmService.update(999L, filmEntity);

        assertNull(result);
    }

    @Test
    @Sql("/setup-films.sql")
    @Sql(scripts = "/cleanup-films.sql", executionPhase = AFTER_TEST_METHOD)
    void givenExistingFilm_whenDelete_thenFilmIsDeleted() {
        filmService.delete(2L);

        var result = filmService.find(2L);
        assertEquals(Option.none(), result);
    }
}