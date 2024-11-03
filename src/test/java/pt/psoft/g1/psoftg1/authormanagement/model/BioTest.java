package pt.psoft.g1.psoftg1.authormanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import pt.psoft.g1.psoftg1.shared.model.StringUtilsCustom;

public class BioTest {

    @Test
    void ensureBioMustNotBeNull() {
        // Arrange
        String nullBio = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Bio(nullBio));
        assertEquals("Bio cannot be null", exception.getMessage());
    }

    @Test
    void ensureBioMustNotBeBlank() {
        // Arrange
        String emptyBio = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Bio(emptyBio));
        assertEquals("Bio cannot be blank", exception.getMessage());
        
        // Arrange
        String whitespaceBio = "   ";

        // Act & Assert
        exception = assertThrows(IllegalArgumentException.class, () -> new Bio(whitespaceBio));
        assertEquals("Bio cannot be blank", exception.getMessage());
    }

    @Test
    void ensureBioMustNotBeOversize() {
        // Arrange
        String oversizedBio = "a".repeat(4097);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Bio(oversizedBio));
        assertEquals("Bio has a maximum of 4096 characters", exception.getMessage());
    }

    @Test
    void ensureBioIsSet() {
        // Arrange
        String bioContent = "Some bio";

        // Act
        Bio bio = new Bio(bioContent);

        // Assert
        assertEquals(bioContent, bio.toString());
    }

    @Test
    void ensureBioIsChanged() {
        // Arrange
        Bio bio = new Bio("Some bio");
        String newBioContent = "Some other bio";

        // Act
        bio.setBio(newBioContent);

        // Assert
        assertEquals(newBioContent, bio.toString());
    }

    @Test
    void ensureBioSanitization() {
    // Arrange
    String bioWithHtml = "<script>alert('XSS');</script> Hello World!";

    // Act
    Bio bio = new Bio(bioWithHtml);

    // Assert
    String sanitizedBio = StringUtilsCustom.sanitizeHtml(bioWithHtml);
    assertEquals(sanitizedBio, bio.toString());
    assertEquals(" Hello World!", bio.toString());
    }


    @Test
    void ensureExceptionThrownForNullInSetBio() {
        // Arrange
        Bio bio = new Bio("Initial bio");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bio.setBio(null));
        assertEquals("Bio cannot be null", exception.getMessage());
    }

    @Test
    void ensureExceptionThrownForBlankInSetBio() {
        // Arrange
        Bio bio = new Bio("Initial bio");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bio.setBio(""));
        assertEquals("Bio cannot be blank", exception.getMessage());
        
        exception = assertThrows(IllegalArgumentException.class, () -> bio.setBio("   "));
        assertEquals("Bio cannot be blank", exception.getMessage());
    }

    @Test
    void ensureExceptionThrownForOversizeInSetBio() {
        // Arrange
        Bio bio = new Bio("Initial bio");
        String oversizedBio = "a".repeat(4097);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bio.setBio(oversizedBio));
        assertEquals("Bio has a maximum of 4096 characters", exception.getMessage());
    }
}
