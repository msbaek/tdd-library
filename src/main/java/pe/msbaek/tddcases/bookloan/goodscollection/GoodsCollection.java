package pe.msbaek.tddcases.bookloan.goodscollection;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
public class GoodsCollection {
    private Long id;
    private String name;
    private Long createdBy;
    private LocalDateTime createdAt;
    private Long updatedBy;
    private LocalDateTime updatedAt;
    private Set<GoodsCollectionItem> goodsCollectionItems = new HashSet<>();

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
