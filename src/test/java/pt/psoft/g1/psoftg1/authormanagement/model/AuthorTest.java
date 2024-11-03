package pt.psoft.g1.psoftg1.authormanagement.model;

import org.hibernate.StaleObjectStateException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pt.psoft.g1.psoftg1.authormanagement.services.CreateAuthorRequest;
import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.shared.model.EntityWithPhoto;
import pt.psoft.g1.psoftg1.shared.model.Photo;

class AuthorTest {
    private final String validName = "João Alberto";
    private final String validBio = "O João Alberto nasceu em Chaves e foi pedreiro a maior parte da sua vida.";
    private final UpdateAuthorRequest request = new UpdateAuthorRequest(validName, validBio, null, null);

    private static class EntityWithPhotoImpl extends EntityWithPhoto { }

    @BeforeEach
    void setUp() {
    }

    @Test
    void ensureNameNotNull() {
        // Arrange

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Author(null, validBio, null), 
                     "Expected IllegalArgumentException when name is null");
    }

    @Test
    void ensureBioNotNull() {
        // Arrange

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Author(validName, null, null), 
                     "Expected IllegalArgumentException when bio is null");
    }

    @Test
    void whenVersionIsStaleItIsNotPossibleToPatch() {
        // Arrange
        final var subject = new Author(validName, validBio, null);

        // Act & Assert
        assertThrows(StaleObjectStateException.class, () -> subject.applyPatch(999, request), 
                     "Expected StaleObjectStateException when version is stale");
    }

    @Test
    void testCreateAuthorWithoutPhoto() {
        // Arrange
        String noPhoto = null;

        // Act
        Author author = new Author(validName, validBio, noPhoto);

        // Assert
        assertNotNull(author, "Author should not be null");
        assertNull(author.getPhoto(), "Photo should be null when not provided");
    }

    @Test
    void testCreateAuthorRequestWithPhoto() {
        // Arrange
        CreateAuthorRequest request = new CreateAuthorRequest(validName, validBio, null, "photoTest.jpg");

        // Act
        Author author = new Author(request.getName(), request.getBio(), "photoTest.jpg");

        // Assert
        assertNotNull(author, "Author should not be null");
        assertEquals(request.getPhotoURI(), author.getPhoto().getPhotoFile(), 
                     "Photo URI should match the expected value");
    }

    @Test
    void testCreateAuthorRequestWithoutPhoto() {
        // Arrange
        CreateAuthorRequest request = new CreateAuthorRequest(validName, validBio, null, null);

        // Act
        Author author = new Author(request.getName(), request.getBio(), null);

        // Assert
        assertNotNull(author, "Author should not be null");
        assertNull(author.getPhoto(), "Photo should be null when not provided");
    }

    @Test
    void testEntityWithPhotoSetPhotoInternalWithValidURI() {
        // Arrange
        EntityWithPhoto entity = new EntityWithPhotoImpl();
        String validPhotoURI = "photoTest.jpg";

        // Act
        entity.setPhoto(validPhotoURI);

        // Assert
        assertNotNull(entity.getPhoto(), "Photo should be set and not null");
    }

    @Test
    void ensurePhotoCanBeNull_AkaOptional() {
        // Arrange

        // Act
        Author author = new Author(validName, validBio, null);

        // Assert
        assertNull(author.getPhoto(), "Photo should be null when not provided");
    }

    @Test
    void ensureValidPhoto() {
        // Arrange
        String photoURI = "photoTest.jpg";

        // Act
        Author author = new Author(validName, validBio, photoURI);
        Photo photo = author.getPhoto();

        // Assert
        assertNotNull(photo, "Photo should not be null when valid URI is provided");
        assertEquals(photoURI, photo.getPhotoFile(), "Photo URI should match the expected value");
    }

    @Test
    void testCreateAuthorWithEmptyName() {
        // Arrange
        String emptyName = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Author(emptyName, validBio, null), 
                     "Expected IllegalArgumentException when name is empty");
    }

    @Test
    void testCreateAuthorWithEmptyBio() {
        // Arrange
        String emptyBio = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Author(validName, emptyBio, null), 
                     "Expected IllegalArgumentException when bio is empty");
    }

    @Test
    void testCreateAuthorWithLongName() {
        // Arrange
        String longName = "A".repeat(1000);

        // Act
        Author author = new Author(longName, validBio, null);

        // Assert
        assertNotNull(author, "Author should be created even with a long name");
    }

    @Test
    void testCreateAuthorWithNonexistentPhoto() {
        // Arrange
        String nonexistentPhotoURI = "nonexistent.jpg";

        // Act
        Author author = new Author(validName, validBio, nonexistentPhotoURI);

        // Assert
        assertNotNull(author.getPhoto(), "Photo should not be null even if the file doesn't exist");
        assertEquals(nonexistentPhotoURI, author.getPhoto().getPhotoFile(), 
                     "Photo URI should match the given URI even if the file is nonexistent");
    }
}
