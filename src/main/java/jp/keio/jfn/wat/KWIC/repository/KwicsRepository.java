package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.domain.Kwics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jfn on 3/28/16.
 */
@Repository
public interface KwicsRepository extends CrudRepository<Kwics, Long> {
    Kwics findById(int id);

    int countByKwicSentenceAndPlace(int sentenceID, int place);

}
