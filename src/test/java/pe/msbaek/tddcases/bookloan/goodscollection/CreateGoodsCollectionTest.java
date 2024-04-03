package pe.msbaek.tddcases.bookloan.goodscollection;

import com.ktown4u.utils.Neutralizer;
import com.ktown4u.utils.YamlPrinter;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

class CreateGoodsCollectionTest {
    private CreateGoodsCollection createGoodsCollection;
    private InMemoryGoodsCollectionRepository repository;
    private List<Goods> goodsList;

    @BeforeEach
    void setUp() {
        repository = new InMemoryGoodsCollectionRepository();
        createGoodsCollection = new CreateGoodsCollection(repository);
        goodsList = repository.readGoods();
    }

    @Test
    void createGoodsCollection() {
        CreateGoodsCollection.CreateGoodsCollectionRequest request = new CreateGoodsCollection.CreateGoodsCollectionRequest(
                "상품군 이름",
                IntStream.range(0, 3)
                        .mapToObj(i -> goodsList.get(i))
                        .map(Goods::goodsId)
                        .toList()
        );
        Long id = createGoodsCollection.createGoodsCollection(request);
        GoodsCollection result = repository.findById(id).get();
        Approvals.verify(Neutralizer.localDateTime(YamlPrinter.printWithExclusions(result, "updatedBy", "updatedAt")));
    }
}