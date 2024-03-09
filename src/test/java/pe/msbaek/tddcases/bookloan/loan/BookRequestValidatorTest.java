package pe.msbaek.tddcases.bookloan.loan;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookRequestValidatorTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("title은 blank일 수 없다")
    void title_cannot_be_blank() {
        BookController.Request request = new BookController.Request("", "author", "2021-01-01");
        validateAndAssertMessag(request, "title is required");

        request = new BookController.Request(null, "author", "2021-01-01");
        validateAndAssertMessag(request, "title is required");
    }

    @Test
    @DisplayName("author는 blank일 수 없다")
    void author_cannot_be_blank() {
        BookController.Request request = new BookController.Request("title", "", "2021-01-01");
        validateAndAssertMessag(request, "author is required");

        request = new BookController.Request("title", null, "2021-01-01");
        validateAndAssertMessag(request, "author is required");
    }

    @Test
    @DisplayName("publishedDate는 yyyy-MM-dd 형식이어야 한다")
    void publishedDate_should_have_yyyy_MM_dd_format() {
        BookController.Request request = new BookController.Request("title", "author", "2021-01-01T00:00:00");
        validateAndAssertMessag(request, "publishedDate should have yyyy-MM-dd format");

        request = new BookController.Request("title", "author", "");
        validateAndAssertMessag(request, "publishedDate should have yyyy-MM-dd format");

        request = new BookController.Request("title", "author", "2024-01-02");
        var violations = validator.validate(request);
        assertThat(violations.size()).isEqualTo(0);
    }

    private void validateAndAssertMessag(BookController.Request request, String expectedMessage) {
        var violations = validator.validate(request);
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }
}