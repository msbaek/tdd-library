package pe.msbaek.tddcases.bookloan.goodscollection;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class InMemoryGoodsCollectionRepository implements GoodsCollectionRepository {
    private List<Goods> goodsList;
    private Map<Long, GoodsCollection> goodsCollectionMap;
    private AtomicLong goodsCollectionIdGenerator = new AtomicLong(1);
    private AtomicLong goodsCollectionItemIdGenerator = new AtomicLong(1);
    InMemoryGoodsCollectionRepository() {
        init();
    }

    private void init() {
        goodsCollectionMap = IntStream.range(0, 40)
                .mapToLong(i -> nextId())
                .mapToObj(id -> new GoodsCollection(id, "name" + id, 1L, LocalDateTime.now()))
                .collect(toMap(GoodsCollection::getId, goodsCollection -> goodsCollection));
        goodsList = List.of(
                new Goods(111839L, "GD00111839", "8809888410251"),
                new Goods(111838L, "GD00111838", "9000000111838"),
                new Goods(111836L, "GD00111836", "8809973502397"),
                new Goods(111835L, "GD00111835", "8809973502397"),
                new Goods(111834L, "GD00111834", "8809973502397"),
                new Goods(111833L, "GD00111833", "8809973502397"),
                new Goods(111832L, "GD00111832", "8809973502397"),
                new Goods(111831L, "GD00111831", "8809973502397"),
                new Goods(111830L, "GD00111830", "8809973502397"),
                new Goods(111826L, "GD00111826", "8809966901251"));
    }

    private Long nextId() {
        return goodsCollectionIdGenerator.getAndIncrement();
    }

    private Long nextItemId() {
        return goodsCollectionItemIdGenerator.getAndIncrement();
    }

    @Override
    public List<GoodsCollection> findByNameContaining(String name, Pageable pageable) {
        return goodsCollectionMap.values().stream()
                .filter(goodsCollection -> goodsCollection.getName().contains(name))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();
    }

    @Override
    public Optional<GoodsCollection> findById(Long id) {
        return Optional.ofNullable(goodsCollectionMap.get(id));
    }

    @Override
    public List<Goods> listGoodsByIds(List<String> ids) {
        return goodsList.stream()
                .filter(goods -> ids.contains(goods.goodsId()) || ids.contains(goods.barcode()))
                .toList();
    }

    @Override
    public GoodsCollection save(GoodsCollection goodsCollection) {
        goodsCollection.setId(nextId());
        goodsCollection.getGoodsCollectionItems().stream()
                        .forEach(i -> i.setId(nextItemId()));
        goodsCollectionMap.put(goodsCollection.getId(), goodsCollection);
        return goodsCollection;
    }
}