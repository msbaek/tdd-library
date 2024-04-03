package pe.msbaek.tddcases.bookloan.goodscollection;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class InMemoryGoodsCollectionRepository implements GoodsCollectionRepository {
    static final Map<Long, GoodsCollection> goodsCollectionMap;
    static AtomicLong goodsCollectionIdGenerator = new AtomicLong(1);

    static {
        goodsCollectionMap = IntStream.range(0, 40)
                .mapToLong(i -> nextId())
                .mapToObj(id -> new GoodsCollection(id, "name" + id, 1L, LocalDateTime.now()))
                .collect(toMap(GoodsCollection::getId, goodsCollection -> goodsCollection));
    }

    private static Long nextId() {
        return goodsCollectionIdGenerator.getAndIncrement();
    }

    @Override
    public List<GoodsCollection> findByNameContaining(String name, Pageable pageable) {
        return goodsCollectionMap.values().stream()
                .filter(goodsCollection -> goodsCollection.getName().contains(name))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();
    }
}