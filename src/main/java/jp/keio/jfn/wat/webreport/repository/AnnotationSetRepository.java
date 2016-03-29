package jp.keio.jfn.wat.webreport.repository;

import jp.keio.jfn.wat.webreport.domain.AnnotationSet;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jfn on 12/22/15.
 */
public interface AnnotationSetRepository extends CrudRepository<AnnotationSet, Long> {
    AnnotationSet findById(Integer id);
}
