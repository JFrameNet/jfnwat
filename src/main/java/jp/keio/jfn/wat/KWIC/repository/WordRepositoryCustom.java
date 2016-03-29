package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.domain.Kwicword;

import java.util.List;

/**
 * Created by jfn on 3/23/16.
 */
public interface WordRepositoryCustom {
    List<Kwicword> findKwicwordLike(String wordElement);
}
