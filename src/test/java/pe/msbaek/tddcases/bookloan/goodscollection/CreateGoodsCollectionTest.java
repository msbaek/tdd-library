package pe.msbaek.tddcases.bookloan.goodscollection;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class CreateGoodsCollectionTest {
    private CreateGoodsCollection createGoodsCollection;
    private InMemoryGoodsCollectionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryGoodsCollectionRepository();
        createGoodsCollection = new CreateGoodsCollection(repository);
    }

    @Test
    void createGoodsCollection() {
        CreateGoodsCollection.CreateGoodsCollectionRequest request = new CreateGoodsCollection.CreateGoodsCollectionRequest("상품군 이름", List.of("GD00111839", "GD00111838", "GD00111836"));
        Long id = createGoodsCollection.createGoodsCollection(request);
        GoodsCollection result = repository.findById(id).get();
        Approvals.verify(result);
    }
}