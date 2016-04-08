package jp.keio.jfn.wat.KWIC.controller;

import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import jp.keio.jfn.wat.KWIC.domain.Kwics;

import jp.keio.jfn.wat.domain.LexUnit;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import jp.keio.jfn.wat.KWIC.repository.KwicSentenceRepository;
import jp.keio.jfn.wat.KWIC.repository.WordRepository;
import jp.keio.jfn.wat.KWIC.repository.KwicsRepository;

import jp.keio.jfn.wat.repository.LexUnitRepository;
import jp.keio.jfn.wat.repository.LexemeRepository;
import jp.keio.jfn.wat.repository.WordFormRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Quiri on 3-2-2016.
 */

@ManagedBean
@RestController
@Scope("view")
public class KwicController implements Serializable {

    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    LexemeRepository lexemeRepository;

    @Autowired
    WordFormRepository wordFormRepository;

    @Autowired
    WordRepository wordRepository;

    @Autowired
    KwicSentenceRepository kwicSentenceRepository;

    @Autowired
    KwicsRepository kwicsRepository;


    private String search ="";
    private String colloquial ="";
    private Boolean end;
    private String sentences;


    public String toKwic() { return "kwicPage?faces-redirect=true";}


    public List<String> completeText(String query) { //todo doesnt get called to first time after pageload
        System.out.println("get complete text");

        List<String> results = new ArrayList<String>();

        while(results.size()<=5) {// TODO make 5 a parameter linked to the 5 in max results in kwicform.xhtml
            for (LexUnit lu : lexUnitRepository./*findLULike*/findByNameStartingWith(query)) {
                String word = lu.getName().split("\\.")[0];
                if(!results.contains(word)){ results.add(word);}
            }
            break;
        }
        return results;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearch() {return search;}

    public void setColloquial(String colloquial) {
        this.colloquial = colloquial;
    }

    public String getColloquial() {
        return colloquial;
    }

    public void setEnd(Boolean end) {
        this.end = end;
    }

    public Boolean getEnd() {
        return end;
    }

    @Transactional(transactionManager = "kwicTransactionManager")
    public void findSentences() {
        System.out.println("get sentences");

        long startTime = System.currentTimeMillis();

        KwicWord word = wordRepository.findByWord(search);
        Set<Kwics> set = word.getKwics();
        if(set.size() == 0) sentences = "No matching sentences found for "+ search;

        if(!colloquial.isEmpty()) {
            KwicWord coWord = wordRepository.findByWord(colloquial);
            if (end) {
                set = keepEndOnly(set);
            } else {
                Set<Kwics> coSet = coWord.getKwics();
                set.retainAll(coSet);
            }
        }else if(end) {
            set = keepEndOnly(set);
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);

        sentences = "finish this sentence, found: "+ set.size();
        System.out.println(sentences);
    }

    private Set<Kwics> keepEndOnly(Set<Kwics> all){
        Set<Kwics> ends = new HashSet<Kwics>();
        for(Kwics kwic: all){
            int place = kwic.getPlace();
            int count = kwicsRepository.countByKwicSentenceAndPlace(kwic.getSentenceID(), place++);
            if(count == 0) ends.add(kwic);
        }
        return ends;
    }

    public void setSentences(String sentences) {
        this.sentences = sentences;
    }

    public String getSentences() {
        return sentences;
    }
}

