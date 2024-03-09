package pe.msbaek.tddcases.bookloan.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktown4u.utils.YamlPrinter;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class RegisterBookWebMvcTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private BookRepository bookRepository;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void register_book_successfully() throws Exception {
        String publishedDateString = "1925-04-10";
        BookController.Request request = new BookController.Request("The Great Gatsby", "F. Scott Fitzgerald", publishedDateString);
        Book book = new Book("The Great Gatsby", "F. Scott Fitzgerald", LocalDate.parse(publishedDateString));
        given(bookRepository.save(Mockito.<Book>any())).willReturn(book);

        MvcResult result = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        BookController.Response response = objectMapper.readValue(result.getResponse().getContentAsString(), BookController.Response.class);
        Approvals.verify(YamlPrinter.print(response));
    }

    @Test
    @DisplayName("필수 정보가 누락된 경우 어떤 필수 정보가 누락되었는지 알려주는 에러 메시지를 반환한다")
    void register_book_failed_because_of_required_items() throws Exception {
        String publishedDateString = "1925-04-10";
        BookController.Request request = new BookController.Request(null, "F. Scott Fitzgerald", publishedDateString);
        MvcResult result = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();

        Approvals.verify(result.getResponse().getContentAsString());
    }
}