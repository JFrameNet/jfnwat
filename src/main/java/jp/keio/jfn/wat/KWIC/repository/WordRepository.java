package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jfn on 3/22/16.
 */
@Repository
public interface WordRepository extends CrudRepository<KwicWord, Long>, WordRepositoryCustom {
    KwicWord findById(int id);
    KwicWord findByWord(String word);

    List<KwicWord> findKwicwordLike(String wordElement);
}
