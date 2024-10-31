package pt.psoft.g1.psoftg1.genremanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Genre {
    @Transient
    private final int GENRE_MAX_LENGTH = 100;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    @Size(min = 1, max = GENRE_MAX_LENGTH, message = "Genre name must be between 1 and 100 characters")
    @Column(unique = true, nullable = false, length = GENRE_MAX_LENGTH)
    @Getter
    private String genre;

    // Controle de unicidade em memória
    private static final Set<String> existingGenres = new HashSet<>();

    protected Genre() {}

    public Genre(String genre) {
        setGenre(genre);
    }

    private void setGenre(String genre) {
        if (genre == null)
            throw new IllegalArgumentException("Genre cannot be null");
        if (genre.isBlank())
            throw new IllegalArgumentException("Genre cannot be blank");
        if (genre.length() > GENRE_MAX_LENGTH)
            throw new IllegalArgumentException("Genre has a maximum of 100 characters");
        if (existingGenres.contains(genre))
            throw new IllegalArgumentException("Genre name must be unique");

        this.genre = genre;
        existingGenres.add(genre); // Adiciona ao conjunto de gêneros existentes
    }

    public String toString() {
        return genre;
    }
}
