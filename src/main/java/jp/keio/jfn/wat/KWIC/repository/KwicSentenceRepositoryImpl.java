package jp.keio.jfn.wat.KWIC.repository;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import jp.keio.jfn.wat.KWIC.domain.KwicSentence;
import java.util.List;

/**
 * Created by jfn on 3/28/16.
 */
public class KwicSentenceRepositoryImpl implements KwicSentenceRepositoryCustom {

    @PersistenceContext(unitName = "kwic")
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<KwicSentence> findWithWord(int wordID) {
        return this.entityManager.
                createQuery("select 1 from Kwicsentence s where s.id in (select kwicsentence from Kwics k where k.word = '"+wordID+"')").
                getResultList();
    }
}
