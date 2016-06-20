package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.DTOKwicSearch;
import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import jp.keio.jfn.wat.KWIC.domain.Kwics;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by jfn on 6/8/16.
 */
public interface KwicsRepositoryCustom {

    Stream<Kwics> readAllAndStream(DTOKwicSearch param);
    List<Kwics> findNRandom(DTOKwicSearch param);

    List<Kwics> sorttest(KwicWord word);
}
