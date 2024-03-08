package pe.msbaek.tddcases.bookloan.loan;

import jakarta.persistence.*;
import pe.msbaek.tddcases.bookloan.loan.Book;

@Entity
public class BookInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private int quantity; // 현재 재고 수량

    // 생성자, 게터 및 세터 생략

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        this.quantity -= amount;
    }
}