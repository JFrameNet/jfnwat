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

    public boolean hasCollocate;
    public boolean end;
    public boolean random;

    public List<KwicWord> words;
    public List<KwicWord> collocates;


    public DTOKwicSearch(String keyWord){
        this.word = keyWord;
    }

    public boolean equals(DTOKwicSearch obj) {

        return obj != null
                && this.word.equals(obj.word)
                && this.corpora.equals(obj.corpora)
                && this.collocate.equals(obj.collocate)
                && this.PRE_COLLOCATE == obj.PRE_COLLOCATE
                && this.POST_COLLOCATE == obj.POST_COLLOCATE
                && this.END_SCOPE == obj.END_SCOPE
                && this.randomNumber == obj.randomNumber
                && this.hasCollocate == obj.hasCollocate
                && this.end == obj.end
                && this.random == obj.random;
    }
}
