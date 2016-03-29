package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.domain.Kwicword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jfn on 3/22/16.
 */
@Repository
public interface WordRepository extends CrudRepository<Kwicword, Long>, WordRepositoryCustom {
    Kwicword findById(int id);
    Kwicword findByWord(String word);


    List<Kwicword> findKwicwordLike(String wordElement);
}
