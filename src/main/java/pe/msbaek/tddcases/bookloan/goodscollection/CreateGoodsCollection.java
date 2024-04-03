package pe.msbaek.tddcases.bookloan.goodscollection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Controller
@PrimaryAdapter
public class CreateGoodsCollection {
    @MutationMapping("createGoodsCollection")
    public Long createGoodsCollection(@Argument final CreateGoodsCollectionRequest request) {
        log.info("request: {}", request);
        return 1l;
    }

    record CreateGoodsCollectionRequest(String name, List<String> ids) {
    }
}