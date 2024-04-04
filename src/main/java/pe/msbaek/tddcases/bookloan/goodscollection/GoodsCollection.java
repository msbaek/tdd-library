package pe.msbaek.tddcases.bookloan.goodscollection;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "GOODS_COLLECTION")
@Comment("특정 상품군")
public class GoodsCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("특정 상품군 ID")
    private Long id;
    @Column(name = "NM")
    @Comment("특정 상품군 이름")
    private String name;
    @Column(name = "CREATED_BY")
    @Comment("특정 상품군 생성한 사용자 ID")
    private Long createdBy;
    @Column(name = "CREATED_AT")
    @Comment("특정 상품군 생성일")
    private LocalDateTime createdAt;
    @Column(name = "UPDATED_BY")
    @Comment("특정 상품군 수정한 사용자 ID")
    private Long updatedBy;
    @Column(name = "UPDATED_AT")
    @Comment("특정 상품군 수정일")
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "goodsCollection", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Comment("특정 상품군에 속한 상품들")
    private List<GoodsCollectionItem> goodsCollectionItems = new ArrayList<>();

    public GoodsCollection(long id, String name, long createdBy, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public GoodsCollection(String name, Long createdBy) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.createdBy = createdBy;
    }

    public void add(GoodsCollectionItem item) {
        goodsCollectionItems.add(item);
    }

    public void setId(Long id) {
        this.id = id;
    }
}
