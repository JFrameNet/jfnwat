package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import jp.keio.jfn.wat.KWIC.domain.Kwics;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jfn on 6/8/16.
 */
public class KwicsRepositoryImpl implements KwicsRepositoryCustom{
    @PersistenceContext(unitName = "kwic")
    private EntityManager entityManager;


    @Override
    public List<Kwics> selectRandomByWord(List<String> corpora, List<KwicWord> words, int number) {

        List<Integer> ids = null;
        try {
            ids = findAllIDByWord(corpora, words);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Integer> random = getXRandomFromList(ids, number);
        return getKwicsByRandomIDs(random);
    }

    @Override
    public List<Kwics> selectRandomWithCollocate(List<String> corpora, List<KwicWord> words, List<KwicWord> collocates, int number) {
        List<Integer> ids = findAllIDByCollocate(corpora, words, collocates);
        List<Integer> random = getXRandomFromList(ids, number);
        return getKwicsByRandomIDs(random);
    }


    @Override
    public List<Kwics> selectRandomWithScopedCollocate(List<String> corpora, List<KwicWord> words, List<KwicWord> collocates, int before, int after, int number) {
        List<Integer> ids = findAllIDByCollocateWithScope(corpora, words, collocates, before, after);
        List<Integer> random = getXRandomFromList(ids, number);
        return getKwicsByRandomIDs(random);
    }

    @Override
    public List<Kwics> selectRandomWithEnd(List<String> corpora, List<KwicWord> words, KwicWord dot, int scope, int number) {
        List<Integer> ids = findAllIDBySentenceEnd(corpora, words, dot, scope);
        List<Integer> random = getXRandomFromList(ids, number);
        return getKwicsByRandomIDs(random);
    }

    @Override
    public List<Kwics> selectRandomWithScopedCollocateAndEnd(List<String> corpora, List<KwicWord> words, List<KwicWord> collocates, int before, int after, KwicWord dot, int scope, int number) {
        List<Integer> ids = findAllIDByCollocateWithScopeAndEnd(corpora, words, collocates, before, after, dot, scope);
        List<Integer> random = getXRandomFromList(ids, number);
        return getKwicsByRandomIDs(random);
    }


    private List<Integer> findAllIDByWord(List<String> corpora,List<KwicWord> words) {
        return this.entityManager.
                createQuery("select k.id from Kwics k where k.kwicSentence.corpusName in :corpusList and k.word in :wordList").
                setParameter("corpusList", corpora).
                setParameter("wordList", words).
                getResultList();
    }


    private List<Integer> findAllIDByCollocate(List<String> corpora, List<KwicWord> words,List<KwicWord> collocates) {
        return this.entityManager.
                createQuery("select k.id from Kwics k where k.kwicSentence.corpusName in :corpusList and k.word in :wordList" +
                        " and exists (select c from Kwics c where c.kwicSentence = k.kwicSentence and c.word in :collocateList)").
                setParameter("corpusList", corpora).
                setParameter("wordList", words).
                setParameter("collocateList", collocates).
                getResultList();
    }


    private List<Integer> findAllIDByCollocateWithScope(List<String> corpora,List<KwicWord> words,List<KwicWord> collocates,int before,int after) {
        return this.entityManager.
                createQuery("select k.id from Kwics k where k.kwicSentence.corpusName in :corpusList and k.word in :wordList " +
                        "and exists (select c from Kwics c where c.kwicSentence = k.kwicSentence and c.word in :collocateList and c.place between (k.place - :b) and (k.place + :a) )").
                setParameter("corpusList", corpora).
                setParameter("wordList", words).
                setParameter("collocateList", collocates).
                setParameter("b", before).
                setParameter("a", after).
                getResultList();
    }


    private List<Integer> findAllIDBySentenceEnd(List<String> corpora,List<KwicWord> words,KwicWord dot,int scope) {
        return this.entityManager.
                createQuery("select k.id from Kwics k where k.kwicSentence.corpusName in :corpusList and k.word in :wordList " +
                        "and exists (select d from Kwics d where d.kwicSentence = k.kwicSentence and d.word = :dot and d.place between (k.place) and (k.place + :endScope) )").
                setParameter("corpusList", corpora).
                setParameter("wordList", words).
                setParameter("dot", dot).
                setParameter("endScope", scope).
                getResultList();
    }


    private List<Integer> findAllIDByCollocateWithScopeAndEnd(List<String> corpora,List<KwicWord> words,List<KwicWord> collocates,int before,int after,KwicWord dot,int scope) {
        return this.entityManager.
                createQuery("select k.id from Kwics k where k.kwicSentence.corpusName in :corpusList and k.word in :wordList " +
                        "and exists (select d from Kwics d where d.kwicSentence = k.kwicSentence and d.word = :dot and d.place between (k.place) and (k.place + :endScope) )"+
                        "and exists (select c from Kwics c where c.kwicSentence = k.kwicSentence and c.word in :collocateList and c.place between (k.place - :b) and (k.place + :a) )").
                setParameter("corpusList", corpora).
                setParameter("wordList", words).
                setParameter("collocateList", collocates).
                setParameter("b", before).
                setParameter("a", after).
                setParameter("dot", dot).
                setParameter("endScope", scope).
                getResultList();
    }

    private List<Integer> getXRandomFromList(List<Integer> list, int x){
        List<Integer> randomIDs = new ArrayList<Integer>();
        int range = list.size();
        Random randomGenerator = new Random();
        for(int i = 0; i<x; i++){
            int randomIndex = randomGenerator.nextInt(range);
            randomIDs.add(list.get(randomIndex));
        }
        return randomIDs;
    }

    private List<Kwics> getKwicsByRandomIDs(List<Integer> ids ){
        return this.entityManager.
                createQuery("select k from Kwics k where k.id in :ids order by k.word").
                setParameter("ids", ids).
                getResultList();
    }

    public List<Kwics> sorttest(KwicWord word){
        return this.entityManager.createNativeQuery("SELECT k.* FROM Kwics k " +
                "LEFT JOIN KwicWord w On (k.word_id = w.id) " +
                "WHERE k.word_id = ? " +
                "ORDER BY (SELECT w.word FROM Kwics o " +
                "WHERE o.sentence_id = k.sentence_id AND o.place = (k.place - 1))", Kwics.class).
                setParameter(1, word).
                setMaxResults(100).
                getResultList();
    }

}
