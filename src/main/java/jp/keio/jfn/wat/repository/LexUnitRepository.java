package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.LexUnit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LexUnitRepository extends CrudRepository<LexUnit, Long>, LexUnitRepositoryCustom {
    LexUnit findById(int id);
    List<LexUnit> findAll();

    List<LexUnit> findLULike(String wordElement);

    List<LexUnit> findByNameStartingWith(String wordElement);
}