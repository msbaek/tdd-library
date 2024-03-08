package pe.msbaek.tddcases.bookloan.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookController {
    @PostMapping("/books")
    public ResponseEntity<Response> addBook(@RequestBody Request request) {
        return new ResponseEntity<>(
                new Response(request.title(), request.author(), request.publishedDate()),
                HttpStatus.OK);
    }

    record Request(String title, String author, String publishedDate) {}
    record Response(String title, String author, String publishedDate) {}
}