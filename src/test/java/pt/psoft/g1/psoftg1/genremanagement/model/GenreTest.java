package pt.psoft.g1.psoftg1.genremanagement.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreTest {

    @Test
    void ensureGenreMustNotBeNull() {
        // Arrange
        String genreName = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Genre(genreName));
    }

    @Test
    void ensureGenreMustNotBeBlank() {
        // Arrange
        String genreName = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Genre(genreName));
    }

    @Test
    void ensureGenreMustNotBeOversize() {
        // Arrange
        String genreName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam venenatis semper nisl, eget condimentum felis tempus vitae.";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Genre(genreName));
    }

    @Test
    void ensureGenreIsSet() {
        // Arrange
        String genreName = "Some genre";

        // Act
        Genre genre = new Genre(genreName);

        // Assert
        assertEquals("Some genre", genre.toString());
    }

    @Test
    void ensureGenreNameIsUnique() {
        // Arrange
        String genreName = "Unique Genre";

        // Act
        Genre firstGenre = new Genre(genreName);

        // Assert
        assertEquals(genreName, firstGenre.toString());
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Genre(genreName), 
                "Should throw exception for duplicate genre name");
    }
}
