package pt.psoft.g1.psoftg1.lendingmanagement.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@PropertySource({"classpath:config/library.properties"})
class LendingTest {
    private static final ArrayList<Author> authors = new ArrayList<>();
    private static Book book;
    private static ReaderDetails readerDetails;
    
    @Value("${lendingDurationInDays}")
    private int lendingDurationInDays;
    
    @Value("${fineValuePerDayInCents}")
    private int fineValuePerDayInCents;

    @BeforeAll
    public static void setup() {
        Author author = new Author("Manuel Antonio Pina",
                "Manuel António Pina foi um jornalista e escritor português, premiado em 2011 com o Prémio Camões",
                null);
        authors.add(author);
        book = new Book("9782826012092",
                "O Inspetor Max",
                "conhecido pastor-alemão que trabalha para a Judiciária, vai ser fundamental para resolver um importante caso de uma rede de malfeitores que quer colocar uma bomba num megaconcerto de uma ilustre cantora",
                new Genre("Romance"),
                authors,
                null);
        readerDetails = new ReaderDetails(1,
                Reader.newReader("manuel@gmail.com", "Manuelino123!", "Manuel Sarapinto das Coives"),
                "2000-01-01",
                "919191919",
                true,
                true,
                true,
                null,
                null);
    }

    @Test
    void ensureBookNotNull() {
        // Arrange
        ReaderDetails validReaderDetails = readerDetails;
        int validSeq = 1;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> new Lending(null, validReaderDetails, validSeq, lendingDurationInDays, fineValuePerDayInCents));
    }

    @Test
    void ensureReaderNotNull() {
        // Arrange
        Book validBook = book;
        int validSeq = 1;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> new Lending(validBook, null, validSeq, lendingDurationInDays, fineValuePerDayInCents));
    }

    @Test
    void ensureValidReaderNumber() {
        // Arrange
        Book validBook = book;
        ReaderDetails validReaderDetails = readerDetails;
        int invalidSeq = -1;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> new Lending(validBook, validReaderDetails, invalidSeq, lendingDurationInDays, fineValuePerDayInCents));
    }

    @Test
    void testSetReturned() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        
        // Act
        lending.setReturned(0, null);
        
        // Assert
        assertEquals(LocalDate.now(), lending.getReturnedDate());
    }

    @Test
    void testGetDaysDelayed() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);

        // Act
        int daysDelayed = lending.getDaysDelayed();

        // Assert
        assertEquals(0, daysDelayed);
    }

    @Test
    void testGetDaysUntilReturn() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);

        // Act
        Optional<Integer> daysUntilReturn = lending.getDaysUntilReturn();

        // Assert
        assertEquals(Optional.of(lendingDurationInDays), daysUntilReturn);
    }

    @Test
    void testGetDaysOverDue() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);

        // Act
        Optional<Integer> daysOverdue = lending.getDaysOverdue();

        // Assert
        assertEquals(Optional.empty(), daysOverdue);
    }

    @Test
    void testGetTitle() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        
        // Act
        String title = lending.getTitle();
        
        // Assert
        assertEquals("O Inspetor Max", title);
    }

    @Test
    void testGetLendingNumber() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        
        // Act
        String lendingNumber = lending.getLendingNumber();
        
        // Assert
        assertEquals(LocalDate.now().getYear() + "/1", lendingNumber);
    }

    @Test
    void testGetBook() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        
        // Act
        Book actualBook = lending.getBook();
        
        // Assert
        assertEquals(book, actualBook);
    }

    @Test
    void testGetReaderDetails() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        
        // Act
        ReaderDetails actualReaderDetails = lending.getReaderDetails();
        
        // Assert
        assertEquals(readerDetails, actualReaderDetails);
    }

    @Test
    void testGetStartDate() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        
        // Act
        LocalDate startDate = lending.getStartDate();
        
        // Assert
        assertEquals(LocalDate.now(), startDate);
    }

    @Test
    void testGetLimitDate() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        
        // Act
        LocalDate limitDate = lending.getLimitDate();
        
        // Assert
        assertEquals(LocalDate.now().plusDays(lendingDurationInDays), limitDate);
    }

    @Test
    void testGetReturnedDate() {
        // Arrange
        Lending lending = new Lending(book, readerDetails, 1, lendingDurationInDays, fineValuePerDayInCents);
        
        // Act
        LocalDate returnedDate = lending.getReturnedDate();
        
        // Assert
        assertNull(returnedDate);
    }
}
