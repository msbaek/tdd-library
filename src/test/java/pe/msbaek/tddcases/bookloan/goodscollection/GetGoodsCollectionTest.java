package pe.msbaek.tddcases.bookloan.goodscollection;

import com.ktown4u.utils.Neutralizer;
import com.ktown4u.utils.YamlPrinter;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import pe.msbaek.tddcases.bookloan.common.SearchDto;
import pe.msbaek.tddcases.bookloan.common.SortDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetGoodsCollectionTest {
    private GetGoodsCollection getGoodsCollection = new GetGoodsCollection();

    @Test
    void pagedGoodsCollection() {
        SearchDto request = new SearchDto("type", "name", 0, 10, new SortDto("by", "asc"));
        Page<GetGoodsCollection.GoodsCollectionDto> goodsCollectionDtos = getGoodsCollection.pagedGoodsCollection(request);
        List<GetGoodsCollection.GoodsCollectionDto> content = goodsCollectionDtos.getContent();
        String response = YamlPrinter.printWithExclusions(content, "updatedBy", "updatedAt");
        Approvals.verify(Neutralizer.localDateTime(response));
    }
}