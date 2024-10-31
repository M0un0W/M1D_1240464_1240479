package pt.psoft.g1.psoftg1.readermanagement.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BirthDateTest {

    
    @Test
    void shouldCreateBirthDateWithValidIntegerParameters() {
        // Arrange
        int year = 2000;
        int month = 1;
        int day = 1;

        // Act
        BirthDate birthDate = assertDoesNotThrow(
            () -> new BirthDate(year, month, day), 
            "Should create BirthDate with valid integer parameters"
        );

        // Assert
        assertNotNull(birthDate);
        assertEquals(year, birthDate.getBirthDate().getYear());
        assertEquals(month, birthDate.getBirthDate().getMonthValue());
        assertEquals(day, birthDate.getBirthDate().getDayOfMonth());
    }

    @Test
    void shouldCreateBirthDateWithValidStringFormat() {
        // Arrange
        String validDateString = "2000-01-01";

        // Act
        BirthDate birthDate = assertDoesNotThrow(
            () -> new BirthDate(validDateString), 
            "Should create BirthDate with valid string format"
        );

        // Assert
        assertNotNull(birthDate);
        assertEquals(validDateString, birthDate.getBirthDate().toString());
    }

    @Test
    void shouldThrowExceptionForInvalidStringDateFormat() {
        // Arrange
        String invalidDateFormat = "01-01-2000";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> new BirthDate(invalidDateFormat), 
            "Should throw IllegalArgumentException for invalid date format"
        );

        // Additional Assert
        assertEquals(
            "Provided birth date is not in a valid format. Use yyyy-MM-dd", 
            exception.getMessage()
        );
    }

    @Test
    void shouldConvertBirthDateToCorrectStringRepresentation() {
        // Arrange
        int year = 2000;
        int month = 1;
        int day = 1;
        BirthDate birthDate = new BirthDate(year, month, day);

        // Act
        String birthDateString = birthDate.toString();

        // Assert
        assertEquals(
            "2000-1-1", 
            birthDateString, 
            "toString should return date in specific format"
        );
    }

    
    @Test
    void shouldThrowAccessDeniedExceptionForTooYoungAge() {
        // Arrange
        BirthDate birthDate = new BirthDate(2000, 1, 1);
        
        // Explicitly set the minimum age using reflection
        ReflectionTestUtils.setField(birthDate, "minimumAge", 18);
    
        // Create a date that is definitely too young
        LocalDate tooYoungDate = LocalDate.now().minusYears(16);
    
        // Act & Assert
        AccessDeniedException exception = assertThrows(
            AccessDeniedException.class, 
            () -> new BirthDate(tooYoungDate.getYear(), tooYoungDate.getMonthValue(), tooYoungDate.getDayOfMonth()),
            "Should throw AccessDeniedException for age below minimum"
        );
    
        // Extensive debugging
        System.out.println("Actual exception message: '" + exception.getMessage() + "'");
        System.out.println("Expected substring: 'User must be, at least, 18years old'");
        System.out.println("Contains expected text: " + exception.getMessage().contains("User must be, at least, 18years old"));
    
        // Additional assertion
        assertTrue(
            exception.getMessage().equals("User must be, at least, 18years old"),
            "Exception message should exactly match the expected text"
        );
    }
}