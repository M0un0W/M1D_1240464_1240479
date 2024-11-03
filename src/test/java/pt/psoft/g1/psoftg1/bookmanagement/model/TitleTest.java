package pt.psoft.g1.psoftg1.bookmanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class TitleTest {

    // Constants for reuse
    private static final String VALID_TITLE = "Some title";
    private static final String EMPTY_TITLE = "";
    private static final String NULL_TITLE = null;
    private static final String WHITESPACE_TITLE = "   ";
    private static final String OVERSIZE_TITLE = "A".repeat(129);

    @Test
    void ensureTitleMustNotBeNull() {
        // Arrange
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Title(NULL_TITLE));
        assertEquals("Title cannot be null", exception.getMessage());
    }

    @Test
    void ensureTitleMustNotBeBlank() {
        // Arrange
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Title(EMPTY_TITLE));
        assertEquals("Title cannot be blank", exception.getMessage());
    }

    @Test
    void ensureTitleMustNotBeWhitespace() {
        // Arrange
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Title(WHITESPACE_TITLE));
        assertEquals("Title cannot be blank", exception.getMessage());
    }

    @Test
    void ensureTitleCantStartWithWhitespace() {
        // Arrange
        String inputTitle = " Some title";
        
        // Act
        Title title = new Title(inputTitle);
        
        // Assert
        assertEquals("Some title", title.toString());
    }

    @Test
    void ensureTitleCantEndWithWhitespace() {
        // Arrange
        String inputTitle = "Some title ";
        
        // Act
        Title title = new Title(inputTitle);
        
        // Assert
        assertEquals("Some title", title.toString());
    }

    @Test
    void ensureTitleMustNotBeOversize() {
        // Arrange
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Title(OVERSIZE_TITLE));
        assertEquals("Title has a maximum of 128 characters", exception.getMessage());
    }

    @Test
    void ensureTitleIsSet() {
        // Arrange
        Title title = new Title(VALID_TITLE);
        
        // Act & Assert
        assertEquals(VALID_TITLE, title.toString());
    }

    @Test
    void ensureTitleIsChanged() {
        // Arrange
        Title title = new Title(VALID_TITLE);
        String newTitle = "Some other title";

        // Act
        title.setTitle(newTitle);
        
        // Assert
        assertEquals(newTitle, title.toString());
    }

    @Test
    void ensureSettingTitleToNullThrowsException() {
        // Arrange
        Title title = new Title(VALID_TITLE);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> title.setTitle(NULL_TITLE));
        assertEquals("Title cannot be null", exception.getMessage());
    }

    @Test
    void ensureSettingTitleToBlankThrowsException() {
        // Arrange
        Title title = new Title(VALID_TITLE);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> title.setTitle(EMPTY_TITLE));
        assertEquals("Title cannot be blank", exception.getMessage());
    }

    @Test
    void ensureSettingTitleToOversizeThrowsException() {
        // Arrange
        Title title = new Title(VALID_TITLE);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> title.setTitle(OVERSIZE_TITLE));
        assertEquals("Title has a maximum of 128 characters", exception.getMessage());
    }

    @Test
    void ensureSettingTitleStripsWhitespace() {
        // Arrange
        Title title = new Title("   Some title   ");

        // Act
        String trimmedTitle = title.toString();

        // Assert
        assertEquals("Some title", trimmedTitle);
    }
}
