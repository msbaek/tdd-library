package pe.msbaek.tddcases.bookloan.goodscollection.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import pe.msbaek.tddcases.bookloan.goodscollection.domain.Goods;

import java.util.List;

@Repository
public class GoodsCollectionRepositoryImpl extends AbstractGoodsCollectionRepositoryImpl {
    @PersistenceContext
    private EntityManager em;

    public GoodsCollectionRepositoryImpl(GoodsCollectionJpaRepository jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public List<Goods> listGoodsByIds(List<String> ids) {
        List<Object[]> resultList = em.createNativeQuery("""
                            select g.goods_no goodsno, g.goods_id as goodsid, g.barcode as barcode
                             from goods g
                              where (g.goods_id in :ids)
                              or (g.barcode in :ids)
                """).setParameter("ids", ids).getResultList();
        return resultList.stream()
                .map(result -> Goods.of((Object[]) result))
                .toList();
    }
}
