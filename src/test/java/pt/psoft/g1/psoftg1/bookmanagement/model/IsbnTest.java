package pt.psoft.g1.psoftg1.bookmanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class IsbnTest {

    @Test
    void ensureIsbnMustNotBeNull() {
        // Arrange
        String nullIsbn = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Isbn(nullIsbn));
    }

    @Test
    void ensureIsbnMustNotBeBlank() {
        // Arrange
        String blankIsbn = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Isbn(blankIsbn));
    }

    @Test
    void ensureIsbnMustNotBeOversize() {
        // Arrange
        String oversizedIsbn = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam venenatis semper nisl, eget condimentum felis tempus vitae.";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Isbn(oversizedIsbn));
    }

    @Test
    void ensureIsbn13IsSet() {
        // Arrange
        String validIsbn13 = "9782826012092";

        // Act
        Isbn isbn = new Isbn(validIsbn13);

        // Assert
        assertEquals(validIsbn13, isbn.toString());
    }

    @Test
    void ensureChecksum13IsCorrect() {
        // Arrange
        String invalidIsbn13 = "9782826012099";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Isbn(invalidIsbn13));
    }

    @Test
    void ensureIsbn10IsSet() {
        // Arrange
        String validIsbn10 = "8175257660";

        // Act
        Isbn isbn = new Isbn(validIsbn10);

        // Assert
        assertEquals(validIsbn10, isbn.toString());
    }

    @Test
    void ensureChecksum10IsCorrect() {
        // Arrange
        String invalidIsbn10 = "8175257667";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Isbn(invalidIsbn10));
    }

    @Test
    void ensureIsbn10WithInvalidFormatThrowsException() {
        // Arrange
        String invalidFormatIsbn10 = "817525766A";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Isbn(invalidFormatIsbn10));
    }

    @Test
    void ensureIsbn13WithInvalidFormatThrowsException() {
        // Arrange
        String invalidFormatIsbn13 = "9782826012AB";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Isbn(invalidFormatIsbn13));
    }

    @Test
    void ensureIsbnMustHaveValidLength() {
        // Arrange
        String tooShortIsbn = "123456789";
        String tooLongIsbn = "12345678901234";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Isbn(tooShortIsbn));
        assertThrows(IllegalArgumentException.class, () -> new Isbn(tooLongIsbn));
    }
}
