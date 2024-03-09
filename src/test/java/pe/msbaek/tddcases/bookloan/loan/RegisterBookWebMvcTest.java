package pe.msbaek.tddcases.bookloan.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktown4u.utils.YamlPrinter;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class RegisterBookWebMvcTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private RegisterNewBook registerNewBook;
    @Autowired private ObjectMapper objectMapper;
    private String publishedDateString;
    private String title;
    private String author;

    @BeforeEach
    void setUp() {
        publishedDateString = "1925-04-10";
        title = "The Great Gatsby";
        author = "F. Scott Fitzgerald";
    }

    @Test
    void register_book_successfully() throws Exception {
        BookController.Request request = createBookWith(title);
        Book book = new Book(title, author, LocalDate.parse(publishedDateString));
        given(registerNewBook.registerNewBook(Mockito.<Book>any())).willReturn(book);

        MvcResult result = getMvcResult(request, status().isOk());

        BookController.Response response = objectMapper.readValue(result.getResponse().getContentAsString(), BookController.Response.class);
        Approvals.verify(YamlPrinter.print(response));
    }

    @Test
    @DisplayName("필수 정보가 누락된 경우 어떤 필수 정보가 누락되었는지 알려주는 에러 메시지를 반환한다")
    void register_book_failed_because_of_required_items() throws Exception {
        BookController.Request request = createBookWith(null);
        MvcResult result = getMvcResult(request, status().isBadRequest());

        Approvals.verify(YamlPrinter.print(result.getResponse().getContentAsString()));
    }

    @Test
    @DisplayName("이미 존재하는 도서 정보가 있는 경우 에러 메시지를 반환한다")
    void register_book_failed_because_of_duplicated_book() throws Exception {
        BookController.Request request = createBookWith(title);
        Book book = new Book(title, author, LocalDate.parse(publishedDateString));
        given(registerNewBook.registerNewBook(Mockito.<Book>any())).willThrow(new IllegalArgumentException("title is already exists"));

        MvcResult result = getMvcResult(request, status().isBadRequest());

        Approvals.verify(YamlPrinter.print(result.getResponse().getContentAsString()));
    }

    private MvcResult getMvcResult(BookController.Request request, ResultMatcher expectedStatus) throws Exception {
        return mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(expectedStatus)
                .andReturn();
    }

    private BookController.Request createBookWith(String title) {
        return new BookController.Request(title, author, publishedDateString);
    }
}