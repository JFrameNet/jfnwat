package jp.keio.jfn.wat.KWIC;

import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.KWIC.domain.KwicSentence;
import jp.keio.jfn.wat.webreport.domain.LexUnit;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import jp.keio.jfn.wat.KWIC.domain.Kwicword;
import jp.keio.jfn.wat.KWIC.repository.KwicSentenceRepository;
import jp.keio.jfn.wat.KWIC.repository.WordRepository;
import jp.keio.jfn.wat.webreport.repository.LexUnitRepository;
import jp.keio.jfn.wat.webreport.repository.LexemeRepository;
import jp.keio.jfn.wat.webreport.repository.WordFormRepository;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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


    private String search ="";
    private String colloquial ="";
    private Boolean end;
    private String sentences;


    public String toKwic() { return "kwicPage?faces-redirect=true";}


    public List<String> completeText(String query) { //todo doesnt get called to first time after pageload
        System.out.println("get complete text");

        List<String> results = new ArrayList<String>();

        while(results.size()<=5) {// TODO make 5 a parameter linked to the 5 in max results in kwicform.xhtml
            for (LexUnit lu : lexUnitRepository.findLULike(query)) {
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

    public void findSentences() { // TODO does not work yet
        System.out.println("get sentences");
        List<KwicSentence> sentenceObjectList = kwicSentenceRepository.findWithWord(wordRepository.findByWord(search).getId());
        sentences = sentenceObjectList.get(0).getSentence();
        System.out.println(sentences);
    }

    public void setSentences(String sentences) {
        this.sentences = sentences;
    }

    public String getSentences() {
        return sentences;
    }
}

