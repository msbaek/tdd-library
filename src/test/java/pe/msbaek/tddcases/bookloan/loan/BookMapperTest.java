package pe.msbaek.tddcases.bookloan.loan;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;

import java.time.LocalDate;

@ApplicationModuleTest
class BookMapperTest {
    @Autowired BookMapper bookMapper;

    @Test
    void toDto() {
        // given
        Book book = new Book("title", "author", LocalDate.now());

        // when
        BookDto dto = bookMapper.toDto(book);
        Approvals.verify(dto);
    }

    @Test
    void toEntity() {
        // given
        BookDto dto = new BookDto(1L, "title", "author", "2021-08-01");

        // when
        Book book = bookMapper.toEntity(dto);
        Approvals.verify(book.toString());
    }
}