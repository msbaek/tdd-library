package pe.msbaek.tddcases.bookloan.goodscollection;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Profile("test")
@Repository("goodsCollectionRepository")
public class InMemoryGoodsCollectionRepository implements GoodsCollectionRepository {
    static final Map<Long, GoodsCollection> goodsCollectionMap = new HashMap<Long, GoodsCollection>();
    static AtomicLong goodsCollectionIdGenerator = new AtomicLong(1);

    static {
        for(int i = 0; i < 40; i++) {
            long id = InMemoryGoodsCollectionRepository.goodsCollectionIdGenerator.getAndIncrement();
            InMemoryGoodsCollectionRepository.goodsCollectionMap.put(id, new GoodsCollection(id, "name" + id, 1L, LocalDateTime.now()));
        }
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