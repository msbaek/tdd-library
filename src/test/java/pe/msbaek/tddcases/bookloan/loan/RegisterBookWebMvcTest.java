package pe.msbaek.tddcases.bookloan.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.approvaltests.Approvals;
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
//                .andExpect(content().string(containsString("The Great Gatsby")));
        Approvals.verify(result.getResponse().getContentAsString());
    }
}