package jp.keio.jfn.wat.KWIC;

/**
 * Created by jfn on 5/11/16.
 */
public class DTOKwicSearch {

    String word;
    String collocate;
    int collOfsetBefore;
    int collOfsetAfter;
    boolean end;

    public DTOKwicSearch(String word, String collocate, int before, int after, boolean end){
        this.word = word;
        this.collocate = collocate;
        this.end = end;
        this.collOfsetBefore = before;
        this.collOfsetAfter = after;
    }
}
