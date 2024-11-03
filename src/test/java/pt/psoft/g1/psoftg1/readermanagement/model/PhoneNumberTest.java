package pt.psoft.g1.psoftg1.readermanagement.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class PhoneNumberTest {
    
    @Test
    void ensureValidMobilePhoneNumberIsAccepted() {
        // Arrange
        String validMobileNumber = "912345678";
        
        // Act & Assert
        assertDoesNotThrow(() -> new PhoneNumber(validMobileNumber));
    }

    @Test
    void ensureValidFixedPhoneNumberIsAccepted() {
        // Arrange
        String validFixedNumber = "212345678";
        
        // Act & Assert
        assertDoesNotThrow(() -> new PhoneNumber(validFixedNumber));
    }

    @Test
    void ensureInvalidPhoneNumberThrowsException() {
        // Arrange
        String[] invalidNumbers = {
            "12345678",
            "00123456789",
            "abcdefghij",
            "512345678",
            "91234567",
            "21234567"
        };
        
        // Act & Assert
        for (String number : invalidNumbers) {
            assertThrows(IllegalArgumentException.class, () -> new PhoneNumber(number));
        }
    }

    @Test
    void ensureCorrectStringRepresentation() {
        // Arrange
        PhoneNumber phoneNumber = new PhoneNumber("912345678");
        PhoneNumber anotherPhoneNumber = new PhoneNumber("212345678");
        
        // Act & Assert
        assertEquals("912345678", phoneNumber.toString());
        assertEquals("212345678", anotherPhoneNumber.toString());
    }

    @Test
    void ensurePhoneNumberCannotBeNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber(null));
    }

    @Test
    void ensurePhoneNumberCannotBeEmpty() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber(""));
    }

    @Test
    void ensurePhoneNumberCannotContainSpaces() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("91 2345678")); // Space in number
    }

    @Test
    void ensurePhoneNumberCannotHaveSpecialCharacters() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("91234@678")); // Special character
    }
    
    @Test
    void ensureValidPhoneNumberWithEdgeCase() {
        // Arrange
        String validEdgeCaseMobile = "900000000";
        String validEdgeCaseFixed = "200000000";
        
        // Act & Assert
        assertDoesNotThrow(() -> new PhoneNumber(validEdgeCaseMobile));
        assertDoesNotThrow(() -> new PhoneNumber(validEdgeCaseFixed));
    }
}
