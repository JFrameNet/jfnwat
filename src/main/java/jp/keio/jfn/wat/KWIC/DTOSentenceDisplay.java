package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.KWIC.domain.KwicSentence;

import java.io.IOException;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.util.*;

/**
 * Created by jfn on 4/26/16.
 */
public class DTOSentenceDisplay implements Serializable{

    private KwicSentence kwicSentence;
    private String preContext;
    private String beginning;
    private String keyWord;
    private int splitIndex;
    private String end;
    private String postContext;
    private int CONTEXT_SCOPE = 20;
    private List<String> before5;
    private List<String> after5;
    private String reverseBeginning;

    public DTOSentenceDisplay(int contextScope, KwicSentence kwicSentence,int splitIndex, List<String> before5, List<String> after5) {
        this.CONTEXT_SCOPE = contextScope;
        this.kwicSentence = kwicSentence;
        this.splitIndex = splitIndex;
        this.before5 = before5;
        this.after5 = after5;
        splitInBeginningWordEnd();
        reverseBeginningToSort();
    }

    public DTOSentenceDisplay(KwicSentence kwicSentence, int splitIndex,  List<String> befor5, List<String> after5){
        this(20, kwicSentence, splitIndex, befor5, after5);
        }

    public DTOSentenceDisplay(int contextScope, KwicSentence kwicSentence, int splitIndex) {
        this(contextScope, kwicSentence, splitIndex, new ArrayList<>(), new ArrayList<>());
    }


    public String getPreContext() {
        if (preContext == null){extendBefore();}
        return preContext;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public String getPostContext() {
        if (postContext == null){extendAfter();}
        return postContext;
    }

    public String getFile(){
        return kwicSentence.getFileName();
    }

    public String getCorpus() {return kwicSentence.getCorpusName();}

    public KwicSentence getKwicSentence() {
        return kwicSentence;
    }


    private void splitInBeginningWordEnd() {
        String sentence = kwicSentence.getSentence();
        String[] split = sentence.split(" ");
        beginning = join(split, 0, splitIndex);
        keyWord = split[splitIndex];
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
        builder.append(lastPartToAddFrom(CONTEXT_SCOPE, beginning));
        if(before5 != null) {
            ListIterator<String> iterator = before5.listIterator(before5.size());
            while (builder.length() < CONTEXT_SCOPE && iterator.hasPrevious()) {
                String[] split = iterator.previous().split(" ");
                String subSentence = lastPartToAddFrom(CONTEXT_SCOPE - builder.length(), join(split, 0, split.length));
                builder.insert(0, subSentence);
            }
        }
        preContext = builder.toString();
    }

    private String lastPartToAddFrom(int charsNeeded, String string) {
        int chars = Math.min(charsNeeded, string.length());
        int fromIndex = string.length() - chars;
        return string.substring(fromIndex);
    }

    private void extendAfter(){
        StringBuilder builder = new StringBuilder();
        builder.append(firstPartToAddFrom(CONTEXT_SCOPE, end));
        if(after5 != null) {
            Iterator<String> iterator = after5.iterator();
            while (builder.length() < CONTEXT_SCOPE && iterator.hasNext()) {
                String[] split = iterator.next().split(" ");
                String subSentence = firstPartToAddFrom(CONTEXT_SCOPE - builder.length(), join(split, 0, split.length));
                builder.append(subSentence);
            }
        }
        postContext = builder.toString();
    }

    private String firstPartToAddFrom(int charsNeeded, String sentence) {
        int tillIndex = Math.min(charsNeeded, sentence.length());
        return sentence.substring(0, tillIndex);
    }

    public String getNonKwicDisplay(){
        StringBuilder builder = new StringBuilder();
        builder.append(beginning).append(keyWord).append(end);
        String string = builder.toString();
        string = string.replaceAll(keyWord, "<b>" + keyWord + "</b>");
        return string;
    }

    public void setBefore5(List<String> before5) {
        this.before5 = before5;
        if (preContext == null){extendBefore();}
    }

    public void setAfter5(List<String> after5) {
        this.after5 = after5;
        if (postContext == null){extendAfter();}

    }

    public void setCONTEXT_SCOPE(int charNum){
        this.CONTEXT_SCOPE = charNum;
        extendBefore();
        extendAfter();
    }

    private void reverseBeginningToSort() {
        StringBuilder builder = new StringBuilder();
        reverseBeginning = builder.append(lastPartToAddFrom(CONTEXT_SCOPE, beginning)).reverse().toString();
    }

    public String getReverseBeginning() {
        return reverseBeginning;
    }

    public String getEnd() {
        return end;
    }



    public void write(java.io.BufferedOutputStream out){
        try {
            out.write("[".getBytes());
            out.write(getFile().getBytes());
            out.write(", ".getBytes());
            out.write(getCorpus().getBytes());
            out.write("] ".getBytes());
            out.write(preContext.getBytes());
            out.write("<target>".getBytes());
            out.write(keyWord.getBytes());
            out.write("</target>".getBytes());
            out.write(postContext.getBytes());
            out.write("\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new UncheckedIOException(e);
        }
    }

}




/*
            long startTime = System.currentTimeMillis();
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("get preceding sentence took: "+elapsedTime);
 */
