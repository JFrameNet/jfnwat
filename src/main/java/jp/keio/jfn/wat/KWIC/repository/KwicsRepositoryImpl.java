package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.DTOKwicSearch;
import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import jp.keio.jfn.wat.KWIC.domain.Kwics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Created by jfn on 6/8/16.
 */
public class KwicsRepositoryImpl implements KwicsRepositoryCustom{
    @PersistenceContext(unitName = "kwic")
    private EntityManager entityManager;

    String sqSelect = " select k from Kwics k ";
    String sqSelectID = " select k.id from Kwics k ";
    String sqKeyWord = " k.word in :wordList ";
    String sqCorpus = " k.kwicSentence.corpusName in :corpusList ";
    String sqScopedCollocate = " exists (select c from Kwics c where c.kwicSentence = k.kwicSentence and c.word in :collocateList and c.place between (k.place - :pre) and (k.place + :post ) ) ";
    String sqEndOfSentence = " exists (select d from Kwics d where d.kwicSentence = k.kwicSentence and d.word = :dot and d.place between (k.place) and (k.place + :endScope) ) ";

    private String getQuery(DTOKwicSearch param, String select) {
        StringBuilder builder = new StringBuilder();
        builder.append(select);
        builder.append(" where ").append(sqKeyWord).append(" and ").append(sqCorpus);
        if(param.hasCollocate){ builder.append(" and ").append(sqScopedCollocate);}
        if (param.end) { builder.append(" and ").append(sqEndOfSentence);}
        return builder.toString();
    }

    private Query setParameters(DTOKwicSearch param, Query query) {
        query.setParameter("corpusList", param.corpora);
        query.setParameter("wordList", param.words);
        if(param.hasCollocate){
            query.setParameter("collocateList", param.collocates);
            query.setParameter("pre", param.PRE_COLLOCATE);
            query.setParameter("post", param.POST_COLLOCATE);
        }
        if(param.end){
            query.setParameter("dot",  param.dot);
            query.setParameter("endScope", param.END_SCOPE);
        }
        return query;
    }

    public List<Kwics> findNRandom(DTOKwicSearch param){
        String querySring = getQuery(param, sqSelectID);
        Query query = entityManager.createQuery(querySring);
        query  = setParameters(param, query);
        List<Integer> ids = query.getResultList();
        List<Integer> random = getXRandomFromList(ids, param.randomNumber);
        return getKwicsByRandomIDs(random);
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

    public Stream<Kwics> readAllAndStream(DTOKwicSearch param){
        List resultList;
        if (param.random){
            resultList = findNRandom(param);
        } else {
            String querySring = getQuery(param, sqSelect);
            Query query = entityManager.createQuery(querySring);
            query  = setParameters(param, query);
            resultList = query.getResultList();
        }
        return resultList.stream();
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
