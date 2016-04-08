package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.WordForm;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jfn on 3/22/16.
 */
public interface WordFormRepository extends CrudRepository<WordForm, Long> {
    WordForm findById(int id);
}