package pe.msbaek.tddcases.bookloan.loan;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterBookRestAssuredTest {
    @LocalServerPort private int port;

    @Test
    void register_book_successfully() {
        String publishedDateString = "1925-04-10";
        BookController.Request request = new BookController.Request("The Great Gatsby", "F. Scott Fitzgerald", publishedDateString);

        given()
                .baseUri("http://localhost")
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
        .when()
                .post("/books")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("title", equalTo("The Great Gatsby"));
    }
}