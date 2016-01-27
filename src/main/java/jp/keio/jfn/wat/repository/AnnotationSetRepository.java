package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.AnnotationSet;
import jp.keio.jfn.wat.domain.Layer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jfn on 12/22/15.
 */
public interface AnnotationSetRepository extends CrudRepository<AnnotationSet, Long> {
    AnnotationSet findById(Integer id);
}
