package pe.msbaek.tddcases.bookloan.loan;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    public ResponseEntity<Response> addBook(@RequestBody @Valid Request request) {
        // validate
        bookRepository.findByTitle(request.title())
                .ifPresent(book -> {
                    throw new IllegalArgumentException("title is already exists");
                });

        // save
        LocalDate publishedDate = LocalDate.parse(request.publishedDate());
        bookRepository.save(new Book(request.title(), request.author(), publishedDate));
        Response response = new Response(request.title(), request.author(), request.publishedDate());
        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    record Request(
            @NotBlank(message = "title is required")
            String title,
            @NotBlank(message = "author is required")
            String author,
            @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "publishedDate should have yyyy-MM-dd format")
            String publishedDate
    ) {}
    record Response(String title, String author, String publishedDate) {}
}