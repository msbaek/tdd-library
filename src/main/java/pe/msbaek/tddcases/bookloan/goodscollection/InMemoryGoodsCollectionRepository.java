package pe.msbaek.tddcases.bookloan.goodscollection;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryGoodsCollectionRepository {
    static final Map<Long, GoodsCollection> goodsCollectionMap = new HashMap<Long, GoodsCollection>();
    static AtomicLong goodsCollectionIdGenerator = new AtomicLong(1);

    static {
        for(int i = 0; i < 40; i++) {
            long id = InMemoryGoodsCollectionRepository.goodsCollectionIdGenerator.getAndIncrement();
            InMemoryGoodsCollectionRepository.goodsCollectionMap.put(id, new GoodsCollection(id, "name" + id, 1L, LocalDateTime.now()));
        }
    }

    List<GoodsCollection> findByNameContaining(String name, Pageable pageable) {
        return goodsCollectionMap.values().stream()
                .filter(goodsCollection -> goodsCollection.getName().contains(name))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();
    }
}