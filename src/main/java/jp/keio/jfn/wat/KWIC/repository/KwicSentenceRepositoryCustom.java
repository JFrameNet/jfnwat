package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.domain.KwicSentence;

import java.util.List;

/**
 * Created by jfn on 3/28/16.
 */
public interface KwicSentenceRepositoryCustom {
    List<KwicSentence> findWithWord(int wordID);
}
