package pt.psoft.g1.psoftg1.lendingmanagement.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.hibernate.StaleObjectStateException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.readermanagement.model.BirthDate;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

/**
 * The {@code Lending} class associates a {@code Reader} and a {@code Book}.
 * <p>It stores the date it was registered, the date it is supposed to
 * be returned, and the date it actually was returned.
 * It also stores an optional reader {@code commentary} (submitted at the time of the return) and
 * the {@code Fine}, if applicable.
 * <p>It is identified in the system by an auto-generated {@code id}, and has a unique-constrained
 * natural key ({@code LendingNumber}) with its own business rules.
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames={"LENDING_NUMBER"})})
public class Lending {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    private LendingNumber lendingNumber;

    @NotNull
    @Getter
    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    private Book book;

    @NotNull
    @Getter
    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    private ReaderDetails readerDetails;

    @NotNull
    @Column(nullable = false, updatable = false)
    @Getter
    private LocalDate startDate;

    @NotNull
    @Column(nullable = false)
    @Getter
    private LocalDate limitDate;

    @Temporal(TemporalType.DATE)
    @Getter
    private LocalDate returnedDate;

    @Version
    @Getter
    private long version;

    @Size(min = 0, max = 1024)
    @Column(length = 1024)
    private String commentary = null;

    @Transient
    private Integer daysUntilReturn;

    @Transient
    private Integer daysOverdue;

    @Getter
    private int fineValuePerDayInCents;

    private static final int DEFAULT_CHILDREN_AGE_LIMIT = 10;
    private static final int DEFAULT_JUVENILE_AGE_LIMIT = 18;
    private static final int MAX_DAYS_PER_LENDING = 14;

    /**
     * Constructs a new {@code Lending} object to be persisted in the database.
     * Sets {@code startDate} as the current date, and {@code limitDate} as the current date plus the
     * business specified number of days a reader can take to return the book.
     *
     * @param book                 {@code Book} object, which should be retrieved from the database.
     * @param readerDetails        {@code ReaderDetails} object, which should be retrieved from the database.
     * @param seq                  sequential number, which should be obtained from the year's count on the database.
     * @param lendingDuration      the number of days the lending is allowed for.
     * @param fineValuePerDayInCents value of the fine per overdue day in cents.
     */
    public Lending(Book book, ReaderDetails readerDetails, int seq, int lendingDuration, int fineValuePerDayInCents) {
        this.book = Objects.requireNonNull(book, "Book cannot be null");
        this.readerDetails = Objects.requireNonNull(readerDetails, "ReaderDetails cannot be null");
        this.lendingNumber = new LendingNumber(seq);
        this.startDate = LocalDate.now();
        this.limitDate = LocalDate.now().plusDays(lendingDuration);
        this.returnedDate = null;
        this.fineValuePerDayInCents = fineValuePerDayInCents;

        validateReaderAge();
        setDaysUntilReturn();
        setDaysOverdue();
    }

    private void validateReaderAge() {
        BirthDate birthDate = readerDetails.getBirthDate();
        int age = birthDate.calculateAge();
        if (age < DEFAULT_CHILDREN_AGE_LIMIT) {
            throw new IllegalArgumentException("Reader is too young for lending.");
        }
    }

    public List<Genre> determineEligibleGenres() {
        int age = readerDetails.getBirthDate().calculateAge();
        if (age < DEFAULT_CHILDREN_AGE_LIMIT) {
            return List.of(new Genre("children"));
        } else if (age < DEFAULT_JUVENILE_AGE_LIMIT) {
            return List.of(new Genre("juvenile"));
        } else {
            return readerDetails.getInterestList();
        }
    }

    public void setReturned(final long desiredVersion, final String commentary) {
        if (this.returnedDate != null)
            throw new IllegalArgumentException("Book has already been returned!");

        if (this.version != desiredVersion)
            throw new StaleObjectStateException("Object was already modified by another user", this.pk);

        if (commentary != null)
            this.commentary = commentary;

        this.returnedDate = LocalDate.now();
    }

    public int getDaysDelayed() {
        if (this.returnedDate != null) {
            return Math.max((int) ChronoUnit.DAYS.between(this.limitDate, this.returnedDate), 0);
        } else {
            return Math.max((int) ChronoUnit.DAYS.between(this.limitDate, LocalDate.now()), 0);
        }
    }

    private void setDaysUntilReturn() {
        int daysUntilReturn = (int) ChronoUnit.DAYS.between(LocalDate.now(), this.limitDate);
        if (this.returnedDate != null || daysUntilReturn < 0) {
            this.daysUntilReturn = null;
        } else {
            this.daysUntilReturn = daysUntilReturn;
        }
    }

    private void setDaysOverdue() {
        int days = getDaysDelayed();
        if (days > 0) {
            this.daysOverdue = days;
        } else {
            this.daysOverdue = null;
        }
    }

    public Optional<Integer> getDaysUntilReturn() {
        setDaysUntilReturn();
        return Optional.ofNullable(daysUntilReturn);
    }

    public Optional<Integer> getDaysOverdue() {
        setDaysOverdue();
        return Optional.ofNullable(daysOverdue);
    }

    public Optional<Integer> getFineValueInCents() {
        int days = getDaysDelayed();
        return (days > 0) ? Optional.of(fineValuePerDayInCents * days) : Optional.empty();
    }

    public Title getTitle() {
        return this.book.getTitle();
    }

    public String getLendingNumber() {
        return this.lendingNumber.toString();
    }

    protected Lending() {}

    public static Lending newBootstrappingLending(Book book, ReaderDetails readerDetails, int year, int seq, LocalDate startDate, LocalDate returnedDate, int lendingDuration, int fineValuePerDayInCents) {
        Lending lending = new Lending();
        lending.book = Objects.requireNonNull(book, "Book cannot be null");
        lending.readerDetails = Objects.requireNonNull(readerDetails, "ReaderDetails cannot be null");
        lending.lendingNumber = new LendingNumber(year, seq);
        lending.startDate = startDate;
        lending.limitDate = startDate.plusDays(lendingDuration);
        lending.fineValuePerDayInCents = fineValuePerDayInCents;
        lending.returnedDate = returnedDate;
        return lending;
    }
}
