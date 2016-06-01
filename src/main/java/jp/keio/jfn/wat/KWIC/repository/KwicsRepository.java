package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import jp.keio.jfn.wat.KWIC.domain.Kwics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

    @Query("select k from Kwics k where k.word in :wordList and exists (select c from Kwics c where c.kwicSentence = k.kwicSentence and c.word in :collocateList and c.place between (k.place - :b) and (k.place + :a) )")
    Page<Kwics> findByCollocateWithScope(@Param("wordList") List<KwicWord> words,
                                         @Param("collocateList") List<KwicWord> collocates,
                                         @Param("b") int before,
                                         @Param("a") int after,
                                         Pageable pageable);

    @Query("select k from Kwics k where k.word in :wordList and exists (select c from Kwics c where c.kwicSentence = k.kwicSentence and c.word in :collocateList)")
    Page<Kwics> findByCollocate(@Param("wordList") List<KwicWord> words,
                                @Param("collocateList") List<KwicWord> collocates,
                                Pageable pageable);

    @Query("select k from Kwics k where k.word in :wordList and exists (select c from Kwics c where c.kwicSentence = k.kwicSentence and c.word = :dot and c.place between (k.place) and (k.place + :endScope) )")
    Page<Kwics> findBySentenceEnd(@Param("wordList") List<KwicWord> words,
                                         @Param("dot") KwicWord dot,
                                         @Param("endScope") int scope,
                                         Pageable pageable);


    @Query("select k from Kwics k where k.word in :wordList and exists (select c from Kwics c where c.kwicSentence = k.kwicSentence and c.word in :collocateList and c.place between (k.place - :b) and (k.place + :a) )")
    Page<Kwics> findByCollocateWithScopeAndEnd(@Param("wordList") List<KwicWord> words,
                                         @Param("collocateList") List<KwicWord> collocates,
                                         @Param("b") int before,
                                         @Param("a") int after,
                                         Pageable pageable);

}
