package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.Lemma;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jfn on 2/12/16.
 */
public interface LemmaRepository extends CrudRepository<Lemma, Long> {
}
