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

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Controller
@PrimaryAdapter
public class GetGoodsCollection {
    @QueryMapping("pagedGoodsCollection")
    public Page<GoodsCollectionDto> pagedGoodsCollection(@Argument final SearchDto request) {
        List<GoodsCollectionDto> result = List.of(
                new GoodsCollectionDto(1L, "상품종", 1L, "2022-01-01 00:00:00", 1L, "2022-01-01 00:00:00")
                , new GoodsCollectionDto(2L, "상품종2", 1L, "2022-01-01 00:00:00", 1L, "2022-01-01 00:00:00")
                , new GoodsCollectionDto(3L, "상품종3", 1L, "2022-01-01 00:00:00", 1L, "2022-01-01 00:00:00")
                , new GoodsCollectionDto(4L, "상품종4", 1L, "2022-01-01 00:00:00", 1L, "2022-01-01 00:00:00")
                , new GoodsCollectionDto(5L, "상품종5", 1L, "2022-01-01 00:00:00", 1L, "2022-01-01 00:00:00")
        );
        long total = result.size();
        return new PageImpl<>(result, PageRequest.of(request.page(), request.size()), total);
    }

    public record GoodsCollectionDto(Long id, String name, Long createdBy, String createdAt, Long updatedBy, String updatedAt) {
    }
}