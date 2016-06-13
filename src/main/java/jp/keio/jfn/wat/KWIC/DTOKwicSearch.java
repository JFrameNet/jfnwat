package jp.keio.jfn.wat.KWIC;

import java.util.List;

/**
 * Created by jfn on 5/11/16.
 */
public class DTOKwicSearch {

    String word;
    String collocate;
    int PRE_COLLOCATE;
    int POST_COLLOCATE;
    boolean end;
    int END_SCOPE;
    List<String> corpora;
    boolean random;

    public DTOKwicSearch(String word, String collocate, int before, int after, boolean end, int endScope, List<String> corpora, boolean random){
        this.word = word;
        this.collocate = collocate;
        this.end = end;
        this.PRE_COLLOCATE = before;
        this.POST_COLLOCATE = after;
        this.END_SCOPE = endScope;
        this.corpora = corpora;
        this.random  = random;
    }
}
