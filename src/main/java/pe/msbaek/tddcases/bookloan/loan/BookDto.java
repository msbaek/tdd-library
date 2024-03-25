package pe.msbaek.tddcases.bookloan.loan;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookDto {
        private Long id;
        private String title;
        private String author;
        private String publishedDate;
}