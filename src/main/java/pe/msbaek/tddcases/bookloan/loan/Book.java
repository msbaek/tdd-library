package pe.msbaek.tddcases.bookloan.loan;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

// @Accessors(fluent = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private LocalDate publishedDate;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookCopy> copies = new HashSet<>();

    public Book(String title, String author, LocalDate publishedDate) {
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
    }

    // 생성자, 게터 및 세터 생략

    public void addCopy(BookCopy copy) {
        copies.add(copy);
        copy.setBook(this);
    }

    public void removeCopy(BookCopy copy) {
        copies.remove(copy);
        copy.setBook(null);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishedDate=" + publishedDate +
                ", copies=" + copies +
                '}';
    }
}
