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
    private final RegisterNewBook registerNewBook;

    @PostMapping("/books")
    public ResponseEntity<Response> addBook(@RequestBody @Valid Request request) {
        Book newBook = toModel(request);

        Book registeredBook = registerNewBook.registerNewBook(newBook);

        return getResponseResponseEntity(registeredBook);
    }

    // TODO: use MapStruct
    private Book toModel(Request request) {
        return new Book(request.title(), request.author(), LocalDate.parse(request.publishedDate()));
    }

    private ResponseEntity<Response> getResponseResponseEntity(Book registeredBook) {
        String formattedPublishedDate = registeredBook.getPublishedDate().toString();
        Response response = new Response(registeredBook.getTitle(), registeredBook.getAuthor(), formattedPublishedDate);
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