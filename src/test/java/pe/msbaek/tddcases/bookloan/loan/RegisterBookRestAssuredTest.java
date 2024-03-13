package pe.msbaek.tddcases.bookloan.loan;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterBookRestAssuredTest {
    @LocalServerPort private int port;
    @MockBean private RegisterNewBook registerNewBook;
    private String title;
    private String author;

    @BeforeEach
    void setUp() {
        title = "The Great Gatsby";
        author = "F. Scott Fitzgerald";
    }

    @Test
    void register_book_successfully() {
        String publishedDateString = "1925-04-10";
        BookController.Request book = new BookController.Request(title, author, publishedDateString);
        Book theGreatGatsby = new Book(title, author, LocalDate.parse(publishedDateString));
        BDDMockito.given(registerNewBook.registerNewBook(Mockito.<Book>any())).willReturn(theGreatGatsby);

        given()
                .baseUri("http://localhost")
                .port(port)
                .contentType(ContentType.JSON)
                .body(book)
        .when()
                .post("/books")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("title", equalTo(title));
    }
}