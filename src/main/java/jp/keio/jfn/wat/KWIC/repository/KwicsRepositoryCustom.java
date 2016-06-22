package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.DTOKwicSearch;
import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import jp.keio.jfn.wat.KWIC.domain.Kwics;

import java.util.List;

/**
 * Created by jfn on 6/8/16.
 */
public interface KwicsRepositoryCustom {

    List<Kwics> readAll(DTOKwicSearch param);
    List<Kwics> findNRandom(DTOKwicSearch param);
    long countSearchResults(DTOKwicSearch param);
}
