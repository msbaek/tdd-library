package pe.msbaek.tddcases.bookloan.loan;

import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@PrimaryAdapter
public class RegisterNewBook {
    private final BookRepository bookRepository;

    Book registerNewBook(Book request) {
        // validate
        bookRepository.findByTitle(request.getTitle())
                .ifPresent(book -> {
                    throw new IllegalArgumentException("title is already exists");
                });

        // save
        return bookRepository.save(request);
    }
}