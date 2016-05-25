package jp.keio.jfn.wat.KWIC;

/**
 * Created by jfn on 5/11/16.
 */
public class DTOKwicSearch {

    String word;
    String colloquial;
    boolean end;

    public DTOKwicSearch(String word, String colloquial, boolean end){
        this.word = word;
        this.colloquial = colloquial;
        this.end = end;
    }
}
