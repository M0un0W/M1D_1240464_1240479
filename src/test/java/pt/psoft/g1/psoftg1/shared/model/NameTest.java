package pt.psoft.g1.psoftg1.shared.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    void ensureNameMustNotBeNull() {
        // Arrange & Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Name(null), "Name cannot be null");
    }

    @Test
    void ensureNameMustNotBeBlank() {
        // Arrange & Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Name(""), "Name cannot be blank, nor only white spaces");
    }

    @Test
    void ensureNameMustOnlyBeAlphanumeric() {
        // Arrange & Act
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new Name("Ricardo!"), "Name can only contain alphanumeric characters");
    }

    @Test
    void ensureNameMustNotBeOversize() {
        // Arrange
        String longName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut fermentum venenatis augue, a congue turpis eleifend ut. Etiam fringilla ex nulla, id quis.";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Name(longName), "Name is too long");
    }

    @Test
    void ensureNameIsSetCorrectly() {
        // Arrange
        String expectedName = "Some name";
        
        // Act
        Name name = new Name(expectedName);
        
        // Assert
        assertEquals(expectedName, name.toString(), "The name should be set correctly");
    }

    @Test
    void ensureNameCanBeChanged() {
        // Arrange
        Name name = new Name("Some name");
        String newName = "Some other name";
        
        // Act
        name.setName(newName);
        
        // Assert
        assertEquals(newName, name.toString(), "The name should be changed correctly");
    }

    @Test
    void ensureSetNameMustNotBeNull() {
        // Arrange
        Name name = new Name("Initial name");
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> name.setName(null), "Name cannot be null");
    }

    @Test
    void ensureSetNameMustNotBeBlank() {
        // Arrange
        Name name = new Name("Initial name");
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> name.setName(""), "Name cannot be blank, nor only white spaces");
    }

    @Test
    void ensureSetNameMustOnlyBeAlphanumeric() {
        // Arrange
        Name name = new Name("Initial name");
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> name.setName("NewName!"), "Name can only contain alphanumeric characters");
    }

    @Test
    void ensureSetNameMustNotBeOversize() {
        // Arrange
        Name name = new Name("Initial name");
        String longName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut fermentum venenatis augue, a congue turpis eleifend ut. Etiam fringilla ex nulla, id quis.";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> name.setName(longName), "Name is too long");
    }
}
