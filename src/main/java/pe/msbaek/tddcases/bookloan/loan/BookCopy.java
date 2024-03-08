package pe.msbaek.tddcases.bookloan.loan;

import jakarta.persistence.*;

@Entity
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String copyNumber; // 복사본 고유 번호
    private boolean isAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public void setBook(Book book) {
        this.book = book;
    }
}