package pt.psoft.g1.psoftg1.lendingmanagement.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LendingNumberTest {

    @Test
    void ensureLendingNumberNotNull() {
        // Arrange
        String lendingNumber = null;
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(lendingNumber));
    }

    @Test
    void ensureLendingNumberNotBlank() {
        // Arrange
        String lendingNumber = "";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(lendingNumber));
    }

    @Test
    void ensureLendingNumberNotWrongFormat() {
        // Arrange
        String[] invalidFormats = {"1/2024", "24/1", "2024-1", "2024\\1"};
        
        for (String format : invalidFormats) {
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> new LendingNumber(format));
        }
    }

    @Test
    void ensureLendingNumberIsSetWithString() {
        // Arrange
        String validLendingNumber = "2024/1";
        
        // Act
        LendingNumber ln = new LendingNumber(validLendingNumber);
        
        // Assert
        assertEquals(validLendingNumber, ln.toString());
    }

    @Test
    void ensureLendingNumberIsSetWithSequential() {
        // Arrange
        int sequential = 1;
        
        // Act
        LendingNumber ln = new LendingNumber(sequential);
        
        // Assert
        assertNotNull(ln);
        assertEquals(LocalDate.now().getYear() + "/" + sequential, ln.toString());
    }

    @Test
    void ensureLendingNumberIsSetWithYearAndSequential() {
        // Arrange
        int year = 2024;
        int sequential = 1;
        
        // Act
        LendingNumber ln = new LendingNumber(year, sequential);
        
        // Assert
        assertNotNull(ln);
    }

    @Test
    void ensureSequentialCannotBeNegative() {
        // Arrange
        int year = 2024;
        int sequential = -1;
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(year, sequential));
    }

    @Test
    void ensureYearCannotBeInTheFuture() {
        // Arrange
        int year = LocalDate.now().getYear() + 1;
        int sequential = 1;
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(year, sequential));
    }
}
