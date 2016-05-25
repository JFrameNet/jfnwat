package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.KWIC.domain.KwicSentence;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by jfn on 4/26/16.
 */
public class DTOSentenceDisplay {

    KwicSentence kwicSentence;
    private String before;
    private String beginning;
    private String word;
    private int splitIndex;
    private String end;
    private String after;
    private int charNum = 20;
    private List<String> before5;
    private List<String> after5;

    public DTOSentenceDisplay(int charNum, KwicSentence kwicSentence,int splitIndex, List<String> before5, List<String> after5) {
        this.charNum = charNum;
        this.kwicSentence = kwicSentence;
        this.splitIndex = splitIndex;
        this.before5 = before5;
        this.after5 = after5;
        splitInBeginningWordEnd();
        extendBefore();
        extendAfter();
    }

    public DTOSentenceDisplay(KwicSentence kwicSentence, int splitIndex,  List<String> befor5, List<String> after5){
        this(20, kwicSentence, splitIndex, befor5, after5);
        }


    public String getBefore() {
        return before;
    }

    public String getWord() {
        return word;
    }

    public String getAfter() {
        return after;
    }

    public String getFile(){
        return kwicSentence.getFileName();
    }

    private void splitInBeginningWordEnd() {
        String sentence = kwicSentence.getSentence();
        String[] split = sentence.split(" ");
        beginning = join(split, 0, splitIndex);
        word = split[splitIndex];
        end  = join(split, splitIndex+1, split.length);
    }

    private String join(String[] split, int from, int last) {
        StringBuilder buildString = new StringBuilder();
        for (int i = from; i < last; i++) {
            buildString.append(split[i]);
        }
        return buildString.toString();
    }


    private void extendBefore(){
        StringBuilder builder = new StringBuilder();
        builder.append(lastPartToAddFrom(charNum, beginning));
        ListIterator<String> iterator = before5.listIterator(before5.size());

        while (builder.length() < charNum &&  iterator.hasPrevious()){
            String[] split = iterator.previous().split(" ");
            String subSentence = lastPartToAddFrom(charNum - builder.length(), join(split, 0, split.length));
            builder.insert(0, subSentence);
        }
        before = builder.toString();
    }

    private String lastPartToAddFrom(int charsNeeded, String string) {
        int chars = Math.min(charsNeeded, string.length());
        int fromIndex = string.length() - chars;
        return string.substring(fromIndex);
    }

    private void extendAfter(){
        StringBuilder builder = new StringBuilder();
        builder.append(firstPartToAddFrom(charNum, end));
        Iterator<String> iterator = after5.iterator();
        while (builder.length() < charNum && iterator.hasNext() ){
            String[] split = iterator.next().split(" ");
            String subSentence = firstPartToAddFrom(charNum - builder.length(), join(split, 0, split.length));
            builder.append(subSentence);
        }
        after = builder.toString();
    }

    private String firstPartToAddFrom(int charsNeeded, String sentence) {
        int tillIndex = Math.min(charsNeeded, sentence.length());
        return sentence.substring(0, tillIndex);
    }

    public void setCharNum(int charNum){
        this.charNum = charNum;
        extendBefore();
        extendAfter();
    }
}


/*
            long startTime = System.currentTimeMillis();
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("get preceding sentence took: "+elapsedTime);
 */
