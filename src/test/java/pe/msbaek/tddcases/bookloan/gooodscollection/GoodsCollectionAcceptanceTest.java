package pe.msbaek.tddcases.bookloan.gooodscollection;

import com.ktown4u.utils.YamlPrinter;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import pe.msbaek.tddcases.bookloan.goodscollection.GetGoodsCollection;

import java.util.List;

@AutoConfigureHttpGraphQlTester
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class GoodsCollectionAcceptanceTest {
    @Autowired private HttpGraphQlTester graphQlTester;

    @Test
    public void query_pagedGoodsCollection() throws Exception {
        String queryString = """
                query {
                  pagedGoodsCollection(request: {
                    keyword: "상품군",
                    type: "type",
                    page: 0,
                    size: 10
                  }) {
                    content {
                      id
                      name
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
        Approvals.verify(YamlPrinter.printWithExclusions(result, "createdBy" ,"createdAt" ,"updatedBy" ,"updatedAt"));
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