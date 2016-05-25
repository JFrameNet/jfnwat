package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import jp.keio.jfn.wat.KWIC.domain.Kwics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jfn on 3/28/16.
 */
@Repository
public interface KwicsRepository extends CrudRepository<Kwics, Long> {
    Kwics findById(int id);
    Page<Kwics> findByWord(int wordId, Pageable pageable);

    int countByWordIsIn(List<KwicWord> words);
    Page<Kwics> findByWordIsIn(List<KwicWord> words, Pageable pageable);

    //Page<Kwics> findByWordIsInAndExists_WordIsIn(List<KwicWord> words1, List<KwicWord> words2, Pageable pageable);

}
