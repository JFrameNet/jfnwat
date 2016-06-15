package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.DTOKwicSearch;
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

    String sqSelectID = " select k.id from Kwics k ";
    String sqKeyWord = " k.word in :wordList ";
    String sqCorpus = " k.kwicSentence.corpusName in :corpusList ";
    String sqScopedCollocate = " exists (select c from Kwics c where c.kwicSentence = k.kwicSentence and c.word in :collocateList and c.place between (k.place - :pre) and (k.place + :post ) ) ";
    String sqEndOfSentence = " exists (select d from Kwics d where d.kwicSentence = k.kwicSentence and d.word = :dot and d.place between (k.place) and (k.place + :endScope) ) ";


    @Override
    public List<Kwics> selectRandomByWord(DTOKwicSearch param, List<KwicWord> words) {
        List<Integer> ids = findAllIdWithWord(param, words);
        List<Integer> random = getXRandomFromList(ids, param.randomNumber);
        return getKwicsByRandomIDs(random);
    }

    @Override
    public List<Kwics> selectRandomWithScopedCollocate(DTOKwicSearch param, List<KwicWord> words, List<KwicWord> collocates) {
        List<Integer> ids = findAllIdWithScopedCollocate(param, words, collocates);
        List<Integer> random = getXRandomFromList(ids, param.randomNumber);
        return getKwicsByRandomIDs(random);
    }

    @Override
    public List<Kwics> selectRandomWithEnd(DTOKwicSearch param, List<KwicWord> words) {
        List<Integer> ids = findAllIdWithEnd(param, words);
        List<Integer> random = getXRandomFromList(ids, param.randomNumber);
        return getKwicsByRandomIDs(random);
    }

    @Override
    public List<Kwics> selectRandomWithScopedCollocateAndEnd(DTOKwicSearch param, List<KwicWord> words, List<KwicWord> collocates) {
        List<Integer> ids = findAllIdWithScopedCollocateAndEnd(param, words, collocates);
        List<Integer> random = getXRandomFromList(ids, param.randomNumber);
        return getKwicsByRandomIDs(random);
    }


    private List<Integer> findAllIdWithWord(DTOKwicSearch param, List<KwicWord> words) {
        return this.entityManager.
                createQuery(sqSelectID +" where "+ sqCorpus +" and "+ sqKeyWord).
                setParameter("corpusList", param.corpora).
                setParameter("wordList", words).
                getResultList();
    }

    private List<Integer> findAllIdWithScopedCollocate(DTOKwicSearch param, List<KwicWord> words, List<KwicWord> collocates) {
        return this.entityManager.
                createQuery(sqSelectID +" where "+ sqCorpus +" and "+ sqKeyWord + " and "+ sqScopedCollocate ).
                setParameter("corpusList", param.corpora).
                setParameter("wordList", words).
                setParameter("collocateList", collocates).
                setParameter("pre", param.PRE_COLLOCATE).
                setParameter("post", param.POST_COLLOCATE).
                getResultList();
    }


    private List<Integer> findAllIdWithEnd(DTOKwicSearch param, List<KwicWord> words) {
        return this.entityManager.
                createQuery(sqSelectID +" where "+ sqCorpus +" and "+ sqKeyWord + " and "+ sqEndOfSentence).
                setParameter("corpusList", param.corpora).
                setParameter("wordList", words).
                setParameter("dot",  param.dot).
                setParameter("endScope", param.END_SCOPE).
                getResultList();
    }


    private List<Integer> findAllIdWithScopedCollocateAndEnd(DTOKwicSearch param, List<KwicWord> words, List<KwicWord> collocates) {
        return this.entityManager.
                createQuery(sqSelectID +" where "+ sqCorpus +" and "+ sqKeyWord + " and "+ sqEndOfSentence + " and "+ sqScopedCollocate).
                setParameter("corpusList", param.corpora).
                setParameter("wordList", words).
                setParameter("collocateList", collocates).
                setParameter("pre", param.PRE_COLLOCATE).
                setParameter("post", param.POST_COLLOCATE).
                setParameter("dot",  param.dot).
                setParameter("endScope", param.END_SCOPE).
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
