package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.domain.KwicSentence;

import java.util.List;

/**
 * Created by jfn on 3/28/16.
 */
public interface KwicSentenceRepositoryCustom {
    List<KwicSentence> findWithWord(int wordID);

    List<KwicSentence> findEndsWithWord(int wordId);

    List<KwicSentence> findEndsWithWordAndWithWord(int wordID, int wordID2);

    List<KwicSentence> findWithWordAndWord(int wordId, int wordId2);
}
