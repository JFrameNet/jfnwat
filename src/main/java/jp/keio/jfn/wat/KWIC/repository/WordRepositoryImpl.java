package jp.keio.jfn.wat.KWIC.repository;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import jp.keio.jfn.wat.KWIC.domain.Kwicword;
import java.util.List;

/**
 * Created by jfn on 3/23/16.
 */
public class WordRepositoryImpl implements WordRepositoryCustom {

    @PersistenceContext(unitName = "kwic")
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<Kwicword> findKwicwordLike(String wordElement) {
        return this.entityManager.
        createQuery("select w from Kwicword w where w.word like '"+wordElement+"%' ").
        getResultList();
    }
}
