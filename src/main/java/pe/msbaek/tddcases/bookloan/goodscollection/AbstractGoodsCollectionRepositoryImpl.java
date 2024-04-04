package pe.msbaek.tddcases.bookloan.goodscollection;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractGoodsCollectionRepositoryImpl implements GoodsCollectionRepository {
    private final GoodsCollectionJpaRepository jpaRepository;

    @Override
    public List<GoodsCollection> findByNameContaining(String name, Pageable pageable) {
        return jpaRepository.findByNameContaining(name, pageable);
    }

    @Override
    public Optional<GoodsCollection> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public GoodsCollection save(GoodsCollection goodsCollection) {
        return jpaRepository.save(goodsCollection);
    }
}
