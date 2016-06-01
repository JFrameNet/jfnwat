package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.KWIC.domain.KwicSentence;
import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import jp.keio.jfn.wat.KWIC.domain.Kwics;
import jp.keio.jfn.wat.KWIC.repository.KwicSentenceRepository;
import jp.keio.jfn.wat.KWIC.repository.KwicsRepository;
import jp.keio.jfn.wat.KWIC.repository.WordRepository;
import jp.keio.jfn.wat.domain.Lexeme;
import jp.keio.jfn.wat.domain.WordForm;
import jp.keio.jfn.wat.repository.LexemeRepository;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class KwicTransactions implements Serializable {
    @Autowired
    LexemeRepository lexemeRepository;
    @Autowired
    WordRepository wordRepository;
    @Autowired
    KwicsRepository kwicsRepository;
    @Autowired
    KwicSentenceRepository kwicSentenceRepository;

    List<KwicWord> kwicWordForms = new ArrayList<KwicWord>();
    List<KwicWord> kwicCollocateForms = new ArrayList<KwicWord>();

    DTOKwicSearch searchIn;
    int totalResults;

    private final int SENTENCE_END_SCOPE = 2;

    public KwicTransactions() {
    }

    @Transactional(transactionManager = "kwicTransactionManager")
    public void setNewSearch(DTOKwicSearch search) throws NoResultsExeption {
        this.searchIn = search;
        clear();
        MakeAllFindable();
        totalResults = kwicsRepository.countByWordIsIn(kwicWordForms); // TODO does not take collocate into account
    }

    private void clear() {
        kwicWordForms.clear();
        kwicCollocateForms.clear();
    }

    private void MakeAllFindable() throws NoResultsExeption {
        findAllKwics(searchIn.word, kwicWordForms);
        if (kwicWordForms.isEmpty()) {
            throw new NoResultsExeption();
        }
        if (! (searchIn.collocate == null || searchIn.collocate.equals("") )) {
            findAllKwics(searchIn.collocate, kwicCollocateForms);
        }
    }

    private void findAllKwics(String search, List<KwicWord> kwicForms) throws NoResultsExeption {
        try {
            // in some cases more then one result eg　車　(名詞 and 接尾辞)
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

 /**/         long startTime = System.currentTimeMillis();

        if(!searchIn.collocate.equals("") && searchIn.end){ //BOTH
             page = kwicsRepository.findByWordIsIn(kwicWordForms, makePagable(first, pageSize)); // TODO
        } else if (!searchIn.collocate.equals("") && !searchIn.end) { //COLLOCATE
            page = kwicsRepository.findByCollocateWithScope(kwicWordForms, kwicCollocateForms, searchIn.collOfsetBefore, searchIn.collOfsetAfter, makePagable(first, pageSize));
        } else if(searchIn.collocate.equals("") && searchIn.end) { // END
            KwicWord dot = wordRepository.findByWord("。").get(0);
            page = kwicsRepository.findBySentenceEnd(kwicWordForms, dot, SENTENCE_END_SCOPE, makePagable(first, pageSize)); //TODO 3 as Parameter
        } else {
            page = kwicsRepository.findByWordIsIn(kwicWordForms, makePagable(first, pageSize));
        }
/**/        long stopTime = System.currentTimeMillis();
/**/         long elapsedTime = stopTime - startTime;
/**/         System.out.println("DB Query took: "+elapsedTime);

        totalResults = (int) page.getTotalElements();
        return convertKwicsToDisplay(page, pageSize);
    }

    private Pageable makePagable(int first, int size){
        int page = first/size;
        return new PageRequest(page, size);
    }

// ********************************************************************************************************************

    List<DTOSentenceDisplay> convertKwicsToDisplay(Page<Kwics> kwicData, int pageSize) {
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
        DTOSentenceDisplay display = new DTOSentenceDisplay(kwicSentence, splitIndex, fiveBeforeAndAfter[0], fiveBeforeAndAfter[1]);
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



    public int getCount() {
        return totalResults;
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