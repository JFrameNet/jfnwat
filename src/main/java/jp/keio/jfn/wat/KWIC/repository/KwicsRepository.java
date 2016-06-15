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
import java.util.stream.Stream;

/**
 * Created by jfn on 3/28/16.
 */
@Repository
public interface KwicsRepository extends CrudRepository<Kwics, Long>, KwicsRepositoryCustom {
    String test = "test";

    Kwics findById(int id);

    Page<Kwics> findByWord(int wordId, Pageable pageable);

    int countByWordIsIn(List<KwicWord> words);

    Page<Kwics> findByWordIsIn(List<KwicWord> words, Pageable pageable);
    Page<Kwics> findByKwicSentenceCorpusNameIsInAndWordIsInOrderByWord(List<String> corpora, List<KwicWord> words, Pageable pageable);


    @Query("select k.id from Kwics k where k.kwicSentence.corpusName in :corpusList and k.word in :wordList " +
            "and exists (select c from Kwics c where c.kwicSentence = k.kwicSentence and c.word in :collocateList)" +
            "order by k.word")
    Page<Kwics> findByCollocate(
                                        @Param("corpusList") List<String> corpora,
                                        @Param("wordList") List<KwicWord> words,
                                        @Param("collocateList") List<KwicWord> collocates,
                                        Pageable pageable);

    @Query("select k from Kwics k where k.kwicSentence.corpusName in :corpusList and k.word in :wordList " +
            "and exists (select c from Kwics c where c.kwicSentence = k.kwicSentence and c.word in :collocateList and c.place between (k.place - :b) and (k.place + :a) )" +
            "order by k.word")
    Page<Kwics> findByCollocateWithScope(@Param("corpusList") List<String> corpora,
                                         @Param("wordList") List<KwicWord> words,
                                         @Param("collocateList") List<KwicWord> collocates,
                                         @Param("b") int before,
                                         @Param("a") int after,
                                         Pageable pageable);

    @Query("select k from Kwics k where k.kwicSentence.corpusName in :corpusList and k.word in :wordList " +
            "and exists (select d from Kwics d where d.kwicSentence = k.kwicSentence and d.word = :dot and d.place between (k.place) and (k.place + :endScope) )" +
            "order by k.word")
    Page<Kwics> findBySentenceEnd(@Param("corpusList") List<String> corpora,
                                    @Param("wordList") List<KwicWord> words,
                                    @Param("dot") KwicWord dot,
                                    @Param("endScope") int scope,
                                    Pageable pageable);


    @Query("select k from Kwics k where k.kwicSentence.corpusName in :corpusList and k.word in :wordList " +
            "and exists (select d from Kwics d where d.kwicSentence = k.kwicSentence and d.word = :dot and d.place between (k.place) and (k.place + :endScope) )"+
            "and exists (select c from Kwics c where c.kwicSentence = k.kwicSentence and c.word in :collocateList and c.place between (k.place - :b) and (k.place + :a) )" +
            "order by k.word")
    Page<Kwics> findByCollocateWithScopeAndEnd(@Param("corpusList") List<String> corpora,
                                         @Param("wordList") List<KwicWord> words,
                                         @Param("collocateList") List<KwicWord> collocates,
                                         @Param("b") int before,
                                         @Param("a") int after,
                                               @Param("dot") KwicWord dot,
                                               @Param("endScope") int scope,
                                         Pageable pageable);

    Stream<Kwics> readAllByKwicSentenceCorpusNameIsInAndWordIsInOrderByWord(List<String> corpora, List<KwicWord> words);
            /*
            @Query("select u from User u")
            Stream<User> findAllByCustomQueryAndStream();

            Stream<User> readAllByFirstnameNotNull();

            @Query("select u from User u")
            Stream<User> streamAllPaged(Pageable pageable);
         */

    List<Kwics> selectRandomByWord(List<String> corpora, List<KwicWord> words, int number);
    List<Kwics> selectRandomWithCollocate(List<String> corpora,List<KwicWord> words,List<KwicWord> collocates, int number);
    List<Kwics> selectRandomWithScopedCollocate(List<String> corpora, List<KwicWord> words, List<KwicWord> collocates, int before, int after, int number);
    List<Kwics> selectRandomWithEnd(List<String> corpora, List<KwicWord> words, KwicWord dot, int scope, int number);
    List<Kwics> selectRandomWithScopedCollocateAndEnd(List<String> corpora, List<KwicWord> words, List<KwicWord> collocates, int before, int after, KwicWord dot, int scope, int number);
    List<Kwics> sorttest(KwicWord word);
}

// k.kwicSentence.corpusName in :corpusList and
// @Param("corpusList") List<String> corpora,