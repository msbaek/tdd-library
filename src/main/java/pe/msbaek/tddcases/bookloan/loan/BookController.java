package pe.msbaek.tddcases.bookloan.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookRepository bookRepository;

    @PostMapping("/books")
    public ResponseEntity<Response> addBook(@RequestBody Request request) {
        // validate
        if (request.title() == null || request.title().isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
        // save
        bookRepository.findByTitle(request.title())
                .ifPresent(book -> {
                    throw new IllegalArgumentException("title is already exists");
                });

        LocalDate publishedDate = LocalDate.parse(request.publishedDate());
        bookRepository.save(new Book(request.title(), request.author(), publishedDate));
        Response response = new Response(request.title(), request.author(), request.publishedDate());
        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    record Request(String title, String author, String publishedDate) {}
    record Response(String title, String author, String publishedDate) {}
}