package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.RelationType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
/**
 * Created by jfn on 12/21/15.
 */
public interface RelationTypeRepository extends CrudRepository<RelationType, Long>{
}
