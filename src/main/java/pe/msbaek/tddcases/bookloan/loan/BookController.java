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
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}