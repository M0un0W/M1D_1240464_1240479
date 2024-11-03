package pt.psoft.g1.psoftg1.readermanagement.model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

public class ReaderTest {

    private final Reader mockReader = mock(Reader.class);
    private final String validBirthDate = "2006-10-31";
    private final String validPhoneNumber = "912345678";
    private final String validPhotoURI = "readerPhotoTest.jpg";

    @Test
    void ensureValidReaderDetailsAreCreated() {
        // Arrange
        int readerNumber = 123;

        // Act & Assert
        assertDoesNotThrow(() -> new ReaderDetails(readerNumber, mockReader, validBirthDate, validPhoneNumber, true, false, false, null, null));
    }

    @Test
    void ensureExceptionIsThrownForNullReader() {
        // Arrange
        int readerNumber = 123;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(readerNumber, null, validBirthDate, validPhoneNumber, true, false, false, null, null));
    }

    @Test
    void ensureExceptionIsThrownForNullPhoneNumber() {
        // Arrange
        int readerNumber = 123;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(readerNumber, mockReader, validBirthDate, null, true, false, false, null, null));
    }

    @Test
    void ensureExceptionIsThrownForNoGdprConsent() {
        // Arrange
        int readerNumber = 123;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(readerNumber, mockReader, validBirthDate, validPhoneNumber, false, false, false, null, null));
    }

    @Test
    void ensureGdprConsentIsTrue() {
        // Arrange
        int readerNumber = 123;
        ReaderDetails readerDetails = new ReaderDetails(readerNumber, mockReader, validBirthDate, validPhoneNumber, true, false, false, null, null);

        // Act
        boolean gdprConsent = readerDetails.isGdprConsent();

        // Assert
        assertTrue(gdprConsent);
    }

    @Test
    void ensurePhotoCanBeNull_AkaOptional() {
        // Arrange
        int readerNumber = 123;
        ReaderDetails readerDetails = new ReaderDetails(readerNumber, mockReader, validBirthDate, validPhoneNumber, true, false, false, null, null);

        // Act
        Photo photo = readerDetails.getPhoto();

        // Assert
        assertNull(photo);
    }

    @Test
    void ensureValidPhoto() {
        // Arrange
        int readerNumber = 123;
        ReaderDetails readerDetails = new ReaderDetails(readerNumber, mockReader, validBirthDate, validPhoneNumber, true, false, false, validPhotoURI, null);

        // Act
        Photo photo = readerDetails.getPhoto();

        // Assert
        assertNotNull(photo);
        assertEquals(validPhotoURI, photo.getPhotoFile());
    }

    @Test
    void ensureInterestListCanBeNullOrEmptyList_AkaOptional() {
        // Arrange
        int readerNumber = 123;

        // Act
        ReaderDetails readerDetailsNullInterestList = new ReaderDetails(readerNumber, mockReader, validBirthDate, validPhoneNumber, true, false, false, validPhotoURI, null);
        ReaderDetails readerDetailsInterestListEmpty = new ReaderDetails(readerNumber, mockReader, validBirthDate, validPhoneNumber, true, false, false, validPhotoURI, new ArrayList<>());

        // Assert
        assertNull(readerDetailsNullInterestList.getInterestList());
        assertEquals(0, readerDetailsInterestListEmpty.getInterestList().size());
    }

    @Test
    void ensureInterestListCanTakeAnyValidGenre() {
        // Arrange
        int readerNumber = 123;
        Genre g1 = new Genre("genre1");
        Genre g2 = new Genre("genre2");
        List<Genre> genreList = new ArrayList<>();
        genreList.add(g1);
        genreList.add(g2);

        // Act
        ReaderDetails readerDetails = new ReaderDetails(readerNumber, mockReader, validBirthDate, validPhoneNumber, true, false, false, validPhotoURI, genreList);

        // Assert
        assertEquals(2, readerDetails.getInterestList().size());
    }
}
