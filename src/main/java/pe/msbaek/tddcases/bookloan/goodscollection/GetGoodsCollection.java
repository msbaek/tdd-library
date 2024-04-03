package pe.msbaek.tddcases.bookloan.goodscollection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import pe.msbaek.tddcases.bookloan.common.SearchDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Controller
@PrimaryAdapter
public class GetGoodsCollection {
    private static final Map<Long, GoodsCollection> goodsCollectionMap = new HashMap<>();
    private static AtomicLong goodsCollectionIdGenerator = new AtomicLong(1);
    static {
        for(int i = 0; i < 40; i++) {
            long id = goodsCollectionIdGenerator.getAndIncrement();
            goodsCollectionMap.put(id, new GoodsCollection(id, "name" + id, 1L, LocalDateTime.now()));
        }
    }

    @QueryMapping("pagedGoodsCollection")
    public Page<GoodsCollectionDto> pagedGoodsCollection(@Argument final SearchDto request) {
        List<GoodsCollection> collections = findByNameContaining(request.keyword(), request.pageableWithSort());
        long total = collections.size();
        List<GoodsCollectionDto> result = collections.stream()
                .map(GoodsCollectionDto::of)
                .toList();
        return new PageImpl<>(result, PageRequest.of(request.page(), request.size()), total);
    }

    private List<GoodsCollection> findByNameContaining(String name, Pageable pageable) {
        return goodsCollectionMap.values().stream()
                .filter(goodsCollection -> goodsCollection.getName().contains(name))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();
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