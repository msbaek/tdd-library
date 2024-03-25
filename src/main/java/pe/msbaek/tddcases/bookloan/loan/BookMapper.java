package pe.msbaek.tddcases.bookloan.loan;

import org.mapstruct.*;

import java.time.LocalDate;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    // @Mapping(target = "totalPrice", expression = "java(product.getPrice() * 1.08)") // mapping with expression
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedDate", source = "publishedDate", qualifiedByName = "localDateToString")
    BookDto toDto(Book book);

    @Mapping(target = "publishedDate", source = "publishedDate", qualifiedByName = "stringToLocalDate")
    Book toEntity(BookDto bookDto);

    // default method for mapping
    default String formatPrice(double price) {
        // Custom logic to format the price
        return "$" + String.format("%.2f", price);
    }

    @Named("localDateToString")
    default String localDateToString(java.time.LocalDate localDate) {
        return localDate.toString();
    }

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String locateDateString) {
        return LocalDate.parse(locateDateString);
    }
}