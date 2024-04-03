package pe.msbaek.tddcases.bookloan.goodscollection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import pe.msbaek.tddcases.bookloan.common.SearchDto;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Controller
@PrimaryAdapter
public class GetGoodsCollection {

    private final InMemoryGoodsCollectionRepository inMemoryGoodsCollectionRepository = new InMemoryGoodsCollectionRepository();

    @QueryMapping("pagedGoodsCollection")
    public Page<GoodsCollectionDto> pagedGoodsCollection(@Argument final SearchDto request) {
        List<GoodsCollection> collections = inMemoryGoodsCollectionRepository.findByNameContaining(request.keyword(), request.pageableWithSort());
        long total = collections.size();
        List<GoodsCollectionDto> result = collections.stream()
                .map(GoodsCollectionDto::of)
                .toList();
        return new PageImpl<>(result, PageRequest.of(request.page(), request.size()), total);
    }

    public record GoodsCollectionDto(Long id, String name, Long createdBy, String createdAt, Long updatedBy,
                                     String updatedAt) {
        public static GoodsCollectionDto of(GoodsCollection model) {
            return new GoodsCollectionDto(model.getId(), model.getName(), model.getCreatedBy(),
                    localDateTimeString(model.getCreatedAt()), model.getUpdatedBy(), localDateTimeString(model.getUpdatedAt()));
        }

        private static String localDateTimeString(LocalDateTime dateTime) {
            return dateTime != null ? dateTime.toString() : "";
        }
    }
}