package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.DTOKwicSearch;
import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import jp.keio.jfn.wat.KWIC.domain.Kwics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by jfn on 3/28/16.
 */
@Repository
public interface KwicsRepository extends CrudRepository<Kwics, Long>, KwicsRepositoryCustom {
    String sqSelect = " select k from Kwics k ";
    String sqKeyWord = " k.word in :wordList ";
    String sqCorpus = " k.kwicSentence.corpusName in :corpusList ";
    String sqScopedCollocate = " exists (select c from Kwics c where c.kwicSentence = k.kwicSentence and c.word in :collocateList and c.place between (k.place - :pre) and (k.place + :post ) ) ";
    String sqEndOfSentence = " exists (select d from Kwics d where d.kwicSentence = k.kwicSentence and d.word = :dot and d.place between (k.place) and (k.place + :endScope) ) ";

    Kwics findById(int id);

    Page<Kwics> findByWord(int wordId, Pageable pageable);

    int countByWordIsIn(List<KwicWord> words);

    Page<Kwics> findByWordIsIn(List<KwicWord> words, Pageable pageable);
    Page<Kwics> findByKwicSentenceCorpusNameIsInAndWordIsInOrderByWord(List<String> corpora, List<KwicWord> words, Pageable pageable);


    @Query(sqSelect+" where "+ sqCorpus +" and " + sqKeyWord  + " and " + sqScopedCollocate + "order by k.word")
    Page<Kwics> findByCollocateWithScope(@Param("corpusList") List<String> corpora,
                                         @Param("wordList") List<KwicWord> words,
                                         @Param("collocateList") List<KwicWord> collocates,
                                         @Param("pre") int before,
                                         @Param("post") int after,
                                         Pageable pageable);

    @Query(sqSelect+" where "+ sqCorpus +" and " + sqKeyWord  + " and " + sqEndOfSentence + " order by k.word")
    Page<Kwics> findBySentenceEnd(@Param("corpusList") List<String> corpora,
                                    @Param("wordList") List<KwicWord> words,
                                    @Param("dot") KwicWord dot,
                                    @Param("endScope") int scope,
                                    Pageable pageable);


    @Query(sqSelect+" where "+ sqCorpus +" and " + sqKeyWord  + " and " + sqEndOfSentence + " and " + sqScopedCollocate + " order by k.word")
    Page<Kwics> findByCollocateWithScopeAndEnd(@Param("corpusList") List<String> corpora,
                                               @Param("wordList") List<KwicWord> words,
                                               @Param("collocateList") List<KwicWord> collocates,
                                               @Param("pre") int before,
                                               @Param("post") int after,
                                               @Param("dot") KwicWord dot,
                                               @Param("endScope") int scope,
                                               Pageable pageable);

    Stream<Kwics> readAllAndStream(DTOKwicSearch param);

    List<Kwics> findNRandom(DTOKwicSearch param);

    List<Kwics> sorttest(KwicWord word);
}

// k.kwicSentence.corpusName in :corpusList and
// @Param("corpusList") List<String> corpora,