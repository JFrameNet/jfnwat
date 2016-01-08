package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.Label;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jfn on 1/7/16.
 */
public interface LabelRepository extends CrudRepository<Label, Long> {
}
