package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.domain.KwicSentence;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jfn on 3/28/16.
 */
@Repository
public interface KwicSentenceRepository extends CrudRepository<KwicSentence, Long> {
    KwicSentence findById(int id);

    List<KwicSentence> findByFileNameAndSentencePlaceBetweenOrderBySentencePlace(String fileName, int place, int place2);
    List<KwicSentence> findByFileNameAndSentencePlaceBetween(String fileName, int place, int place2);
}

