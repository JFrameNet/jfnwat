package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.LexUnit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LexUnitRepository extends CrudRepository<LexUnit, Long> {
    LexUnit findById(int id);
    List<LexUnit> findAll();
}