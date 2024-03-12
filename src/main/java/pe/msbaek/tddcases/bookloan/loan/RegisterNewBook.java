package pe.msbaek.tddcases.bookloan.loan;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterNewBook {
    private final BookRepository bookRepository;

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