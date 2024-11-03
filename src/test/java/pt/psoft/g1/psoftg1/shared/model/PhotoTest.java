package pt.psoft.g1.psoftg1.shared.model;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class PhotoTest {
    @Test
    void ensurePathMustNotBeNull() {
        // Arrange
        Path nullPath = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> new Photo(nullPath));
    }

    @Test
    void ensurePathIsValidToLocalFile() {
        // Arrange
        Path validPath = Paths.get("photoTest.jpg");

        // Act
        Photo photo = new Photo(validPath);

        // Assert
        assertEquals("photoTest.jpg", photo.getPhotoFile(), "The photoFile should be set correctly.");
    }

    @Test
    void ensurePhotoFileIsSetCorrectlyWithAbsolutePath() {
        // Arrange
        Path absolutePath = Paths.get("C:\\uploads\\photoTest.jpg").toAbsolutePath();

        // Act
        Photo photo = new Photo(absolutePath);

        // Assert
        assertEquals(absolutePath.toString(), photo.getPhotoFile(), "The photoFile should reflect the absolute path.");
    }

    @Test
    void ensurePhotoFileIsSetCorrectlyWithNormalizedPath() {
        // Arrange
        Path unnormalizedPath = Paths.get("uploads/psoft-g1/../photoTest.jpg").normalize();

        // Act
        Photo photo = new Photo(unnormalizedPath);

        // Assert
        assertEquals(unnormalizedPath.toString(), photo.getPhotoFile(), "The photoFile should be normalized.");
    }

    @Test
    void ensureConstructorHandlesDifferentPathFormats() {
        // Arrange
        Path pathWithDifferentSeparator = Paths.get("uploads-psoft-g1", "photoTest.jpg");

        // Act
        Photo photo = new Photo(pathWithDifferentSeparator);

        // Assert
        assertEquals(pathWithDifferentSeparator.toString(), photo.getPhotoFile(), 
                     "The photoFile should handle different path formats correctly.");
    }

    @Test
    void ensureDefaultConstructorInitializesCorrectly() {
        // Arrange & Act
        Photo photo = new Photo();

        // Assert
        assertNull(photo.getPhotoFile(), "The photoFile should be null when initialized with the default constructor.");
    }

    @Test
    void ensureSetPhotoFileThrowsExceptionOnNull() {
        // Arrange
        Photo photo = new Photo(Paths.get("photoTest.jpg"));

        // Act & Assert
        assertThrows(NullPointerException.class, () -> photo.setPhotoFile(null), 
                     "setPhotoFile should throw NullPointerException on null input.");
    }

    @Test
    void ensureSetPhotoFileUpdatesPhotoFile() {
        // Arrange
        Photo photo = new Photo(Paths.get("photoTest.jpg"));
        String newPath = "newPhotoTest.jpg";

        // Act
        photo.setPhotoFile(newPath);

        // Assert
        assertEquals(newPath, photo.getPhotoFile(), "The photoFile should be updated correctly.");
    }
}
