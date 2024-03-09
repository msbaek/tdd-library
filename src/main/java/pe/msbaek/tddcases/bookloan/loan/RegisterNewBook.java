package pe.msbaek.tddcases.bookloan.loan;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegisterNewBook {
    private BookRepository bookRepository;

    public RegisterNewBook() {
    }

    Book registerNewBook(Book request) {
        // validate
        bookRepository.findByTitle(request.title())
                .ifPresent(book -> {
                    throw new IllegalArgumentException("title is already exists");
                });

        // save
        return bookRepository.save(request);
    }
}