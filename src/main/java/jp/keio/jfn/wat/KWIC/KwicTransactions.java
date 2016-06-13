package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.KWIC.domain.KwicSentence;
import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import jp.keio.jfn.wat.KWIC.domain.Kwics;
import jp.keio.jfn.wat.KWIC.repository.KwicSentenceRepository;
import jp.keio.jfn.wat.KWIC.repository.KwicsRepository;
import jp.keio.jfn.wat.KWIC.repository.WordRepository;
import jp.keio.jfn.wat.Utils;
import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.domain.LexUnit;
import jp.keio.jfn.wat.domain.Lexeme;
import jp.keio.jfn.wat.domain.WordForm;
import jp.keio.jfn.wat.repository.FrameRepository;
import jp.keio.jfn.wat.repository.LexUnitRepository;
import jp.keio.jfn.wat.repository.LexemeRepository;
import org.hibernate.Hibernate;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class KwicTransactions implements Serializable {
    @Autowired
    LexemeRepository lexemeRepository;
    @Autowired
    LexUnitRepository lexUnitRepository;
    @Autowired
    FrameRepository frameRepository;
    @Autowired
    WordRepository wordRepository;
    @Autowired
    KwicsRepository kwicsRepository;
    @Autowired
    KwicSentenceRepository kwicSentenceRepository;

    List<KwicWord> kwicWordForms = new ArrayList<KwicWord>();
    List<KwicWord> kwicCollocateForms = new ArrayList<KwicWord>();

    private KwicWord dot;

    private DTOKwicSearch searchIn;
    private int totalResults;
    public List<DTOSentenceDisplay> curentPageList;

    private int CONTEXT_SCOPE = 20;

    public KwicTransactions() {
    }

    @PostConstruct
    public void intit(){
        dot = wordRepository.findByWord("。").get(0);
    }

    @Transactional(transactionManager = "kwicTransactionManager")
    public void setNewSearch(DTOKwicSearch search) throws UnknownWordExeption {
        this.searchIn = search;
        clear();
        MakeAllFindable();
        totalResults = kwicsRepository.countByWordIsIn(kwicWordForms); // TODO does not take collocate into account
    }

    private void clear() {
        kwicWordForms.clear();
        kwicCollocateForms.clear();
    }

    private void MakeAllFindable() throws UnknownWordExeption {
        findAllKwics(searchIn.word, kwicWordForms);
        if (kwicWordForms.isEmpty()){throw new UnknownWordExeption(UnknownWordExeption.Cause.KEYWORD);}

        if (!searchIn.collocate.equals("")) {
            findAllKwics(searchIn.collocate, kwicCollocateForms);
            if(kwicCollocateForms.isEmpty()){ throw new UnknownWordExeption(UnknownWordExeption.Cause.COLLOCATE);}
        }
    }

    private void findAllKwics(String search, List<KwicWord> kwicForms){
        try {
            // in some cases more then one result eg　車　(名詞 and 接尾辞 part of speach)
            List<WordForm> wordForms = getWordForms(search);
            for (WordForm wordForm : wordForms) {
                findKwics(wordForm.getForm(), kwicForms);
            }
            findKwics(search, kwicForms);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<WordForm> getWordForms(String search) {
        List<Lexeme> lexemes = lexemeRepository.findByName(search);
        List<WordForm> wordForms = new ArrayList<WordForm>();
        for (Lexeme l : lexemes){
            wordForms.addAll(l.getWordForms());
        }
        return wordForms;
    }

    private void findKwics(String word, List<KwicWord> kwicForms) {
        List<KwicWord> kwicSearches = wordRepository.findByWord(word);
        kwicForms.addAll(kwicSearches);
    }


// ********************************************************************************************************************

    @Transactional(transactionManager = "kwicTransactionManager")
    public List<DTOSentenceDisplay> getData(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        Page<Kwics> page;
        List<Kwics> pageList;
        boolean doRandomSearch = searchIn.random;

        /**/ int sortbefore = -5;

 /**/         long startTime = System.currentTimeMillis();

        //TODO REFACTOR THIS BEAST
        if(!searchIn.collocate.equals("") && searchIn.end){ //BOTH
            if(doRandomSearch){
                pageList = kwicsRepository.selectRandomWithScopedCollocateAndEnd(searchIn.corpora, kwicWordForms, kwicCollocateForms, searchIn.PRE_COLLOCATE, searchIn.POST_COLLOCATE, dot, searchIn.END_SCOPE, pageSize);
            } else {
                page = kwicsRepository.findByCollocateWithScopeAndEnd(searchIn.corpora, kwicWordForms, kwicCollocateForms, searchIn.PRE_COLLOCATE, searchIn.POST_COLLOCATE, dot, searchIn.END_SCOPE, makePagable(first, pageSize));
                pageList = pageToList(page);
            }
        } else if (!searchIn.collocate.equals("") && !searchIn.end) { //COLLOCATE
            if(doRandomSearch){
                pageList = kwicsRepository.selectRandomWithScopedCollocate(searchIn.corpora, kwicWordForms, kwicCollocateForms, searchIn.PRE_COLLOCATE, searchIn.POST_COLLOCATE, pageSize);
            } else {
                page = kwicsRepository.findByCollocateWithScope(searchIn.corpora, kwicWordForms, kwicCollocateForms, searchIn.PRE_COLLOCATE, searchIn.POST_COLLOCATE, makePagable(first, pageSize));
                pageList = pageToList(page);
            }
        } else if(searchIn.collocate.equals("") && searchIn.end) { // END
            if(doRandomSearch){
                pageList = kwicsRepository.selectRandomWithEnd(searchIn.corpora, kwicWordForms, dot, searchIn.END_SCOPE, pageSize);
            } else {
                page = kwicsRepository.findBySentenceEnd(searchIn.corpora, kwicWordForms, dot, searchIn.END_SCOPE, makePagable(first, pageSize));
                pageList = pageToList(page);
            }
        } else {
            if(doRandomSearch){
                pageList = kwicsRepository.selectRandomByWord(searchIn.corpora, kwicWordForms, pageSize);
            }
//            else if (sortField.equals("before")) {
 //               kwicsRepository.simpleSorted(searchIn.corpora, kwicWordForms, sortbefore, makePagable(first, pageSize));
//            }
            else {
                page = kwicsRepository.findByKwicSentenceCorpusNameIsInAndWordIsInOrderByWord(searchIn.corpora, kwicWordForms, makePagable(first, pageSize));
                pageList = pageToList(page);
//                KwicWord word = kwicWordForms.get(1);
//                pageList = kwicsRepository.sorttest(word);
            }
        }
/**/        long stopTime = System.currentTimeMillis();
/**/        long elapsedTime = stopTime - startTime;
/**/        System.out.println("DB Query took: "+elapsedTime);


        totalResults = doRandomSearch ? pageSize : totalResults;
        try {
            curentPageList =  convertKwicsToDisplay(pageList, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curentPageList;
    }

    private List<Kwics> pageToList(Page<Kwics> page) {
        totalResults = (int) page.getTotalElements();
        return page.getContent();
    }

    private Pageable makePagable(int first, int size){
        int page = first/size;
        return new PageRequest(page, size);
    }

    public int getCount() {
        return totalResults;
    }

// ********************************************************************************************************************

    public void setCONTEXT_SCOPE(int CONTEXT_SCOPE) {
        this.CONTEXT_SCOPE = CONTEXT_SCOPE;
    }


    List<DTOSentenceDisplay> convertKwicsToDisplay(List<Kwics> kwicData, int pageSize) {
        List<DTOSentenceDisplay> result = new ArrayList<DTOSentenceDisplay>(pageSize);

/**/         long startTime = System.currentTimeMillis();

        for (Kwics kwic : kwicData) {
            KwicSentence kwicSentence = kwic.getKwicSentence();
            int splitIndex = kwic.getPlace();
            result.add(sentenceToDisplay(kwicSentence, splitIndex));
        }

/**/         long stopTime = System.currentTimeMillis();
/**/         long elapsedTime = stopTime - startTime;
/**/         System.out.println("Converting to Display took: "+elapsedTime);

        return result;
    }

    DTOSentenceDisplay sentenceToDisplay(KwicSentence kwicSentence, int splitIndex){
        int currentSentencePlace = kwicSentence.getSentencePlace();
        ArrayList<String>[] fiveBeforeAndAfter = find5BeforeAndAfter(kwicSentence, currentSentencePlace);
        DTOSentenceDisplay display = new DTOSentenceDisplay(CONTEXT_SCOPE, kwicSentence, splitIndex, fiveBeforeAndAfter[0], fiveBeforeAndAfter[1]);
        return display;
    }

    private ArrayList<String>[] find5BeforeAndAfter(KwicSentence kwicSentence, int currentSentencePlace) {
        String corpus = kwicSentence.getCorpusName();
        String file = kwicSentence.getFileName();
        List<KwicSentence> BeforeAndAfter5 = kwicSentenceRepository.findByCorpusNameAndFileNameAndSentencePlaceBetweenOrderBySentencePlace(corpus, file, currentSentencePlace-5, currentSentencePlace+5); //TODO takes ~1000millis
        return separateBeforeAndAfter(currentSentencePlace, BeforeAndAfter5);
    }

    private ArrayList<String>[] separateBeforeAndAfter(int currentSentencePlace, List<KwicSentence> beforeAndAfter5) {
        ArrayList<String>[] result = new ArrayList[2];
        result[0] = new ArrayList<String>(5);
        result[1] = new ArrayList<String>(5);
        int current = Math.min(currentSentencePlace, 5);
        for (int i = 0, BeforeAndAfter5Size = beforeAndAfter5.size(); i < BeforeAndAfter5Size; i++) {
            KwicSentence ks = beforeAndAfter5.get(i);
            if (i < current){
                result[0].add(ks.getSentence());
            } else if (i > current){
                result[1].add(ks.getSentence());
            }
        }
        return result;
    }

// ********************************************************************************************************************

    @Transactional
    public List<Frame> findRelevantFrames(){
        List<Frame> relevantFrames = new ArrayList<Frame>();
        try {
                for (LexUnit lu : lexUnitRepository.findAll()) {
                    if (Utils.matchSearch(searchIn.word, lu.getName())) {
                        Frame frame = lu.getFrame();
                        if (!frame.getName().isEmpty()) {
                            relevantFrames.add(frame);
                            Hibernate.initialize(frame.getFrameElements());
                            Hibernate.initialize(frame.getFrameRelations1());
                            Hibernate.initialize(frame.getFrameRelations2());
                        }
                    }
                }
            // Collections.sort(relevantFrames);
        } catch (Exception e){
            e.printStackTrace();
        }
        return relevantFrames;
    }

/*
    public DTOSentenceDisplay getKwicSentenceByRowKey(String rowKey){
        KwicSentence kwicSentence = kwicSentenceRepository.findById(Integer.parseInt(rowKey));
        return sentenceToDisplay(kwicSentence);
    }
*/

/* Old code

    List<String> find5Between(String file, int from, int to) {
        List<String> five = new ArrayList<String>(5);

        List<KwicSentence> sentencesFB = kwicSentenceRepository.findByFileNameAndSentencePlaceBetweenOrderBySentencePlace(file, from, to);
        for (KwicSentence ks : sentencesFB) {
            five.add(ks.getSentence());
        }
        return five;
    }
*/

}