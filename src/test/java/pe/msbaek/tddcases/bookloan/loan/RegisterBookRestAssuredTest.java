package pe.msbaek.tddcases.bookloan.loan;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterBookRestAssuredTest {
    @LocalServerPort private int port;

    @Test
    void register_book_successfully() {
        Book book = new Book("The Great Gatsby", "F. Scott Fitzgerald", LocalDate.now());

        given()
                .baseUri("http://localhost")
                .port(port)
                .contentType(ContentType.JSON)
                .body(book)
                .when()
                .post("/books")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("title", equalTo("The Great Gatsby"));
    }
}