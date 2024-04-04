package pe.msbaek.tddcases.bookloan.goodscollection;

import com.ktown4u.utils.Neutralizer;
import com.ktown4u.utils.YamlPrinter;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.jdbc.Sql;
import pe.msbaek.tddcases.bookloan.goodscollection.domain.GoodsCollectionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// @ActiveProfiles("test")
@AutoConfigureHttpGraphQlTester
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class GoodsCollectionAcceptanceTest {
    @Autowired private HttpGraphQlTester graphQlTester;
    @Autowired private GoodsCollectionRepository goodsCollectionRepository;

    @Test
    @Sql("classpath:GOODS.sql")
    public void query_pagedGoodsCollection() throws Exception {
        String queryString = """
                query {
                  pagedGoodsCollection(request: {
                    keyword: "name",
                    type: "type",
                    page: 0,
                    size: 10
                  }) {
                    content {
                      id
                      name
                      createdBy
                      createdAt
                      goodsCollectionItems {
                        goodsNo
                        goodsId
                        barcode
                      }
                    }
                  }
               }
                """;

        List<GetGoodsCollection.GoodsCollectionDto> result = request(queryString, "pagedGoodsCollection.content")
                .entityList(GetGoodsCollection.GoodsCollectionDto.class)
                .get();
        Approvals.verify(Neutralizer.localDateTime(YamlPrinter.printWithExclusions(result, "updatedBy" ,"updatedAt")));
    }

    @Test
    @Sql("classpath:GetGoodsCollectionsTest.sql")
    public void create_goods_collection() throws Exception {
        String queryString = """
                mutation {
                    createGoodsCollection(request: {
                        name: "상품군 이름"
                        ids: ["GD00111839", "GD00111838", "GD00111836"]
                    })
                }
                """;

        Long result = request(queryString, "createGoodsCollection")
                .entity(Long.class)
                .get();
        assertThat(result).isGreaterThan(0L);
    }

    private GraphQlTester.Path request(String queryString, String requestName) {
        return this.graphQlTester
                .mutate()
                .header("x-tester-no", "1001")
                .build()
                .document(queryString)
                .execute()
                .path(requestName);
    }
}