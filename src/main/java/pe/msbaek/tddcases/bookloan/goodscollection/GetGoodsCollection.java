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
import pe.msbaek.tddcases.bookloan.goodscollection.domain.GoodsCollection;
import pe.msbaek.tddcases.bookloan.goodscollection.domain.GoodsCollectionItem;
import pe.msbaek.tddcases.bookloan.goodscollection.domain.GoodsCollectionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Controller
@PrimaryAdapter
public class GetGoodsCollection {

    private final GoodsCollectionRepository repository;

    @QueryMapping("pagedGoodsCollection")
    public Page<GoodsCollectionDto> pagedGoodsCollection(@Argument final SearchDto request) {
        List<GoodsCollection> collections = repository.findByNameContaining(request.keyword(), request.pageableWithSort());
        long total = collections.size();
        List<GoodsCollectionDto> result = collections.stream()
                .map(GoodsCollectionDto::of)
                .toList();
        return new PageImpl<>(result, PageRequest.of(request.page(), request.size()), total);
    }

    public record GoodsCollectionItemDto(Long goodsNo, String goodsId, String barcode) {
        static GoodsCollectionItemDto from(GoodsCollectionItem goodsCollectionItem) {
            return new GoodsCollectionItemDto(goodsCollectionItem.getGoodsNo(), goodsCollectionItem.getGoodsId(), goodsCollectionItem.getBarcode());
        }
    }

    public record GoodsCollectionDto(Long id, String name, Long createdBy, String createdAt, Long updatedBy,
                                     String updatedAt, List<GoodsCollectionItemDto> goodsCollectionItems) {
        public static GoodsCollectionDto of(GoodsCollection goodsCollection) {
            List<GoodsCollectionItemDto> goodsCollectionItems = goodsCollection.getGoodsCollectionItems().stream()
                    .map(goodsCollectionItem -> GoodsCollectionItemDto.from(goodsCollectionItem))
                    .toList();
            return of(goodsCollection, goodsCollectionItems);
        }

        private static GoodsCollectionDto of(GoodsCollection goodsCollection, List<GoodsCollectionItemDto> goodsCollectionItems) {
            return new GoodsCollectionDto(goodsCollection.getId(), goodsCollection.getName(),
                    goodsCollection.getCreatedBy(), localDateTimeString(goodsCollection.getCreatedAt()),
                    goodsCollection.getUpdatedBy(), localDateTimeString(goodsCollection.getUpdatedAt()),
                    goodsCollectionItems);
        }

        private static String localDateTimeString(LocalDateTime dateTime) {
            return dateTime != null ? dateTime.toString() : "";
        }
    }
}