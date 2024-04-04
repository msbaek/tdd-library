package pe.msbaek.tddcases.bookloan.goodscollection.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "GOODS_COLLECTION_ITEM")
@Comment("특정 상품군 아이템")
public class GoodsCollectionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("특정 상품군 아이템 ID")
    private Long id;
    @Column(name = "GOODS_NO")
    @Comment("상품 No")
    private Long goodsNo;
    @Column(name = "GOODS_ID")
    @Comment("상품 ID")
    private String goodsId;
    @Column(name = "BARCODE")
    @Comment("상품 바코드")
    private String barcode;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "GOODS_COLLECTION_ID", nullable = false)
    private GoodsCollection goodsCollection;
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