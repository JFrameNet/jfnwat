package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.KWIC.domain.KwicWord;

import java.util.List;

/**
 * Created by jfn on 5/11/16.
 */
public class DTOKwicSearch {

    public String word;

    public List<String> corpora;

    public String collocate;
    public int PRE_COLLOCATE;
    public int POST_COLLOCATE;

    public int END_SCOPE;
    public KwicWord dot;

    public int randomNumber;

    boolean end;
    boolean random;


    public DTOKwicSearch(String word, String collocate, int before, int after, boolean end, int endScope, List<String> corpora, boolean random, int randomNumber){
        this.word = word;
        this.collocate = collocate;
        this.end = end;
        this.PRE_COLLOCATE = before;
        this.POST_COLLOCATE = after;
        this.END_SCOPE = endScope;
        this.corpora = corpora;
        this.random  = random;
        this.randomNumber = randomNumber;
    }
}
