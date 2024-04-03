package pe.msbaek.tddcases.bookloan.goodscollection;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GoodsCollectionRepository {
    List<GoodsCollection> findByNameContaining(String name, Pageable pageable);
    Optional<GoodsCollection> findById(Long id);
}
