package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.Sentence;
import org.springframework.data.repository.CrudRepository;
/**
 * Created by jfn on 2/12/16.
 */
public interface SentenceRepository extends CrudRepository<Sentence, Long>{
    Sentence findById(Integer id);
}
