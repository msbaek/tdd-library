package pe.msbaek.tddcases.bookloan.goodscollection;

import lombok.Getter;

@Getter
public class GoodsCollectionItem {
    private Long id;
    private Long goodsNo;
    private String goodsId;
    private String barcode;

    public static GoodsCollectionItem of(Goods goods) {
        GoodsCollectionItem item = new GoodsCollectionItem();
        item.goodsNo = goods.goodsNo();
        item.goodsId = goods.goodsId();
        item.barcode = goods.barcode();
        return item;
    }

    public void setId(Long id) {
        this.id = id;
    }
}