package pe.msbaek.tddcases.bookloan.goodscollection.infra;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.msbaek.tddcases.bookloan.goodscollection.domain.GoodsCollection;

import java.util.List;

public interface GoodsCollectionJpaRepository extends JpaRepository<GoodsCollection, Long> {
    List<GoodsCollection> findByNameContaining(String name, Pageable pageable);
}
