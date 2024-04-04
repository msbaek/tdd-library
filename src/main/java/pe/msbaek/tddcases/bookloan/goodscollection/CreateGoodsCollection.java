package pe.msbaek.tddcases.bookloan.goodscollection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import pe.msbaek.tddcases.bookloan.goodscollection.domain.Goods;
import pe.msbaek.tddcases.bookloan.goodscollection.domain.GoodsCollection;
import pe.msbaek.tddcases.bookloan.goodscollection.domain.GoodsCollectionItem;
import pe.msbaek.tddcases.bookloan.goodscollection.domain.GoodsCollectionRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Controller
@PrimaryAdapter
public class CreateGoodsCollection {
    private final GoodsCollectionRepository repository;

    @MutationMapping("createGoodsCollection")
    public Long createGoodsCollection(@Argument final CreateGoodsCollectionRequest request) {
        log.info("request: {}", request);
        List<Goods> goodsList = repository.listGoodsByIds(request.ids());
        GoodsCollection goodsCollection = new GoodsCollection(request.name(), userId());
        for (Goods goods : goodsList) {
            goodsCollection.add(GoodsCollectionItem.of(goods));
        }

        GoodsCollection result = repository.save(goodsCollection);
        return result.getId();
    }

    private Long userId() {
        return 1L;
    }

    record CreateGoodsCollectionRequest(String name, List<String> ids) {
    }
}