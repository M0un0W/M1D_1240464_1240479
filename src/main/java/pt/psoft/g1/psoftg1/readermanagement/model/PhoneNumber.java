package pt.psoft.g1.psoftg1.readermanagement.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class PhoneNumber {
    String phoneNumber;

    public PhoneNumber(String phoneNumber) {
        setPhoneNumber(phoneNumber);
    }

    protected PhoneNumber() {}

    private void setPhoneNumber(String number) {
        // Check for null or empty
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }
        
        // Check for non-numeric characters using regex
        if (!number.matches("\\d{9}")) {
            throw new IllegalArgumentException("Phone number must be 9 digits long and numeric.");
        }

        // Check valid starting digits and length
        if (!(number.startsWith("9") || number.startsWith("2"))) {
            throw new IllegalArgumentException("Phone number must start with '9' or '2'.");
        }

        this.phoneNumber = number;
    }

    @Override
    public String toString() {
        return this.phoneNumber;
    }
}
