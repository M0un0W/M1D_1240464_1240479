package pt.psoft.g1.psoftg1.readermanagement.model;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.AccessDeniedException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@PropertySource({"classpath:config/library.properties"})
public class BirthDate {
    @Getter
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    LocalDate birthDate;

    @Transient
    private final String dateFormatRegexPattern = "\\d{4}-\\d{2}-\\d{2}";

    @Transient
    @Value("${minimumReaderAge:18}")
    private int minimumAge = 18; // Provide a default value

    public BirthDate(int year, int month, int day) {
        setBirthDate(year, month, day);
    }

    public BirthDate(String birthDate) {
        if (!birthDate.matches(dateFormatRegexPattern)) {
            throw new IllegalArgumentException("Provided birth date is not in a valid format. Use yyyy-MM-dd");
        }

        String[] dateParts = birthDate.split("-");

        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);

        setBirthDate(year, month, day);
    }

    private void setBirthDate(int year, int month, int day) {
        LocalDate userDate = LocalDate.of(year, month, day);
        LocalDate minimumAgeDate = LocalDate.now().minusYears(minimumAge);

        // Explicit age calculation
        int age = Period.between(userDate, LocalDate.now()).getYears();

        // Debug print statements
        System.out.println("User's birth date: " + userDate);
        System.out.println("Minimum age date: " + minimumAgeDate);
        System.out.println("Calculated age: " + age);
        System.out.println("Minimum age: " + minimumAge);

        // Throw exception if user is younger than minimum age
        if (userDate.isAfter(minimumAgeDate)) {
            throw new AccessDeniedException("User must be, at least, " + minimumAge + " years old");
        }

        this.birthDate = userDate;
    }

    // Method to calculate the age of the user based on the birthDate
    public int calculateAge() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    public String toString() {
        return String.format("%d-%d-%d", this.birthDate.getYear(), this.birthDate.getMonthValue(), this.birthDate.getDayOfMonth());
    }
}
