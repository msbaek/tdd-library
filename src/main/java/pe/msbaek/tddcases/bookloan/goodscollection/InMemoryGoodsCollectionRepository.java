package pe.msbaek.tddcases.bookloan.goodscollection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

@Profile("test")
@Repository
public class InMemoryGoodsCollectionRepository implements GoodsCollectionRepository {
    private List<Goods> goodsList;
    private Map<Long, GoodsCollection> goodsCollectionMap;
    private AtomicLong goodsCollectionIdGenerator = new AtomicLong(1);
    private AtomicLong goodsCollectionItemIdGenerator = new AtomicLong(1);

    InMemoryGoodsCollectionRepository() {
        init();
    }

    private void init() {
        goodsList = loadGoodsList();
        goodsCollectionMap = IntStream.range(0, 40)
                .mapToLong(i -> nextId())
                .mapToObj(id -> createGoodsCollection(id))
                .collect(toMap(GoodsCollection::getId, goodsCollection -> goodsCollection));
    }

    private List<Goods> loadGoodsList() {
        return readGoods();
    }

    private GoodsCollection createGoodsCollection(long id) {
        GoodsCollection goodsCollection = new GoodsCollection(id, "name" + id, 1L, LocalDateTime.now());
        for (int i = 0; i < 3; i++) {
            Goods goods = goodsList.get((int) (id % goodsList.size()));
            GoodsCollectionItem item = GoodsCollectionItem.of(goods);
            item.setId(goodsCollectionItemIdGenerator.getAndIncrement());
            goodsCollection.add(item);
        }
        return goodsCollection;
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

    List<Goods> readGoods() {
        ObjectMapper objectMapper = new ObjectMapper();
        String fileName = "goods.json";
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName)) {
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}