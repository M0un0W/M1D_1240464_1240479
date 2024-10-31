package pt.psoft.g1.psoftg1.bookmanagement.model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

class BookTest {
    private final String validIsbn = "9782826012092";
    private final String validTitle = "Encantos de contar";
    private final String validDescription = "A wonderful journey into the art of storytelling.";
    private final String validPhotoURI = "cover_image_url";
    
    // Unique genres for testing
    private final Genre validGenre = new Genre("Fantasia_" + System.currentTimeMillis());
    
    private List<Author> authors;
    private final Author validAuthor1 = new Author("João Alberto", "O João Alberto nasceu em Chaves e foi pedreiro a maior parte da sua vida.", null);
    private final Author validAuthor2 = new Author("Maria José", "A Maria José nasceu em Viseu e só come laranjas às segundas feiras.", null);

    @BeforeEach
    void setUp() {
        // Arrange: Initialize authors list for each test
        authors = new ArrayList<>();
        authors.add(validAuthor1);
        authors.add(validAuthor2);
    }

    @Test
    void ensureIsbnNotNull() {
        // Arrange
        Book book = new Book(validIsbn, validTitle, validDescription, validGenre, authors, validPhotoURI);

        // Act
        String isbn = book.getIsbn();

        // Assert
        assertNotNull(isbn, "ISBN should not be null");
    }

    @Test
    void ensureTitleNotNull() {
        // Arrange
        Book book = new Book(validIsbn, validTitle, validDescription, validGenre, authors, validPhotoURI);
    
        // Act
        Title title = book.getTitle();  // Assuming Title is a String

        // Assert
        assertNotNull(title, "Title should not be null");
    }
    
    @Test
    void ensureGenreNotNull() {
        // Arrange
        Genre nullGenre = null;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book(validIsbn, validTitle, validDescription, nullGenre, authors, validPhotoURI);
        });
        assertEquals("Genre cannot be null", exception.getMessage(), "Creating a book with a null genre should throw an IllegalArgumentException with the correct message");
    }

    @Test
    void ensureAuthorsNotNull() {
        // Arrange
        List<Author> nullAuthors = null;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book(validIsbn, validTitle, validDescription, validGenre, nullAuthors, validPhotoURI);
        });
        assertEquals("Author list is null", exception.getMessage(), "Creating a book with a null authors list should throw an IllegalArgumentException with the correct message");
    }

    @Test
    void ensureAuthorsNotEmpty() {
        // Arrange
        List<Author> emptyAuthors = new ArrayList<>();

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book(validIsbn, validTitle, validDescription, validGenre, emptyAuthors, validPhotoURI);
        });
        assertEquals("Author list is empty", exception.getMessage(), "Creating a book with an empty authors list should throw an IllegalArgumentException with the correct message");
    }

    @Test
    void ensureBookCreatedWithMultipleAuthors() {
        // Arrange
        Book book = new Book(validIsbn, validTitle, validDescription, validGenre, authors, validPhotoURI);

        // Act
        List<Author> bookAuthors = book.getAuthors();

        // Assert
        assertEquals(2, bookAuthors.size(), "Book should be created with multiple authors");
        assertTrue(bookAuthors.contains(validAuthor1), "Book should contain valid author 1");
        assertTrue(bookAuthors.contains(validAuthor2), "Book should contain valid author 2");
    }
}
