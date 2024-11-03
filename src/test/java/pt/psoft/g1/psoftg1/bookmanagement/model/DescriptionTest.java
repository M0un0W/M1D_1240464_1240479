package pt.psoft.g1.psoftg1.bookmanagement.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class DescriptionTest {

    @Test
    void ensureDescriptionCanBeNull() {
        // Act
        assertDoesNotThrow(() -> new Description(null));
        
        // Assert
        Description description = new Description(null);
        assertNull(description.toString(), "Description should be null for null input.");
    }

    @Test
    void ensureDescriptionMustNotBeOversize() {
        // Arrange
        String oversizedDescription = "A".repeat(4097);
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Description(oversizedDescription));
        
        // Assert
        assertEquals("Description has a maximum of 4096 characters", exception.getMessage());
    }

    @Test
    void ensureDescriptionIsSet() {
        // Arrange
        final var descriptionText = "Some description";
        
        // Act
        final var description = new Description(descriptionText);
        
        // Assert
        assertEquals(descriptionText, description.toString(), "Description should match the input.");
    }

    @Test
    void ensureDescriptionIsChanged() {
        // Arrange
        final var description = new Description("Some description");
        
        // Act
        description.setDescription("Some other description");
        
        // Assert
        assertEquals("Some other description", description.toString(), "Description should be updated.");
    }

    @Test
    void ensureBlankDescriptionIsNull() {
        // Arrange
        final var description = new Description("Some text");
        
        // Act
        description.setDescription("");
        
        // Assert
        assertNull(description.toString(), "Setting a blank description should result in null.");
    }

    @Test
    void ensureHtmlIsSanitized() {
        // Arrange
        String unsanitizedDescription = "<script>alert('test');</script> This is a description.";
        
        // Act
        final var description = new Description(unsanitizedDescription);
        
        // Assert
        String sanitized = description.toString();
        assertFalse(sanitized.contains("<script>"), "Description should not contain HTML tags.");
        assertEquals("This is a description.", sanitized.trim(), "Sanitized description should match the expected output.");
    }
}
