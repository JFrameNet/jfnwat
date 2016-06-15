package jp.keio.jfn.wat.KWIC;

/**
 * Created by jfn on 4/18/16.
 */
public class UnknownWordExeption extends Exception {
    private Cause cause;

    public UnknownWordExeption(Cause cause){
        this.cause = cause;
    }

    public enum Cause{
        KEYWORD, COLLOCATE, END
    }
}
