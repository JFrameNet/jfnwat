package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.KWIC.domain.KwicSentence;

import java.util.List;

/**
 * Created by jfn on 5/13/16.
 */
public class FalsePositiveException extends Throwable {
        KwicSentence kwicSentence;
        List<String> splitWords;

        public FalsePositiveException(KwicSentence kwicSentence, List<String> splitWords) {
            this.kwicSentence = kwicSentence;
            this.splitWords = splitWords;
        }
}
