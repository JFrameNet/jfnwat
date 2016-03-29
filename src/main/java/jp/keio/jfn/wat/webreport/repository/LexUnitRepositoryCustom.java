package jp.keio.jfn.wat.webreport.repository;

import jp.keio.jfn.wat.webreport.domain.LexUnit;

import java.util.List;

/**
 * Created by jfn on 3/29/16.
 */
public interface LexUnitRepositoryCustom {
    List<LexUnit> findLULike(String wordElement);
}
