package jp.keio.jfn.wat.repository;

import jp.keio.jfn.wat.domain.LexUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by jfn on 3/29/16.
 */
public class LexUnitRepositoryImpl implements LexUnitRepositoryCustom {

    @PersistenceContext(unitName = "webreport")
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<LexUnit> findLULike(String wordElement) {
        return this.entityManager.
                createQuery("select l from LexUnit l where l.name like '"+wordElement+"%' ").
                getResultList();
    }
}
