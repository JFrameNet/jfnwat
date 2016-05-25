package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.KWIC.domain.KwicSentence;
import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import jp.keio.jfn.wat.KWIC.domain.Kwics;
import jp.keio.jfn.wat.KWIC.repository.KwicSentenceRepository;
import jp.keio.jfn.wat.KWIC.repository.KwicsRepository;
import jp.keio.jfn.wat.KWIC.repository.WordRepository;
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

    List<String> stringWordForms = new ArrayList<String>();
    List<KwicWord> kwicWordForms = new ArrayList<KwicWord>();
    List<String> stringColloquialForms = new ArrayList<String>();
    List<KwicWord> kwicCollocialForms = new ArrayList<KwicWord>();

    DTOKwicSearch searchIn;
    int totalResults;

    public KwicTransactions() {
    }

    @Transactional(transactionManager = "kwicTransactionManager")
    public void setNewSearch(DTOKwicSearch search) throws NoResultsExeption {
        clear();
        this.searchIn = search;
        MakeAllFindableWithSearch();
        totalResults = kwicsRepository.countByWordIsIn(kwicWordForms);
    }

    private void clear() {
        stringWordForms.clear();
        kwicWordForms.clear();
        stringColloquialForms.clear();
        kwicCollocialForms.clear();
    }

    private void MakeAllFindableWithSearch() throws NoResultsExeption {
        findForms(searchIn.word, stringWordForms, kwicWordForms);
        if (kwicWordForms.isEmpty()) {
            throw new NoResultsExeption();
        }
        if (! (searchIn.colloquial == null || searchIn.colloquial.equals("") )) {
            findForms(searchIn.colloquial, stringColloquialForms, kwicCollocialForms);
        }
    }

    private void findForms(String search, List<String> stringForms, List<KwicWord> kwicForms) throws NoResultsExeption {
        try {
            List<WordForm> wordForms = lexemeRepository.findByName(search).getWordForms();

            for (WordForm wordForm : wordForms) {
                String stringForm = wordForm.getForm();
                KwicWord kwicForm = wordRepository.findByWord(stringForm);
                if (kwicForm != null) {
                    kwicForms.add(kwicForm);
                    stringForms.add(stringForm);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            KwicWord byWord = wordRepository.findByWord(search);
            kwicForms.add(byWord);
            stringForms.add(search);
        } catch (Exception e){
           e.printStackTrace();
        }
    }



/*
    public DTOSentenceDisplay getKwicSentenceByRowKey(String rowKey){
        KwicSentence kwicSentence = kwicSentenceRepository.findById(Integer.parseInt(rowKey));
        return sentenceToDisplay(kwicSentence);
    }
*/



    @Transactional(transactionManager = "kwicTransactionManager")
    public List<DTOSentenceDisplay> getData(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

 //   　  if(searchIn.colloquial == null && !searchIn.end){ //TODO
            Page<Kwics> page = kwicsRepository.findByWordIsIn(kwicWordForms, makePagable(first, pageSize));
 //       }

        totalResults = (int) page.getTotalElements();
        return convertKwicsToDisplay(page, pageSize);
    }

    private Pageable makePagable(int first, int size){
//        int last = Math.min(first+size, getCount());
        int page = first/size;
        return new PageRequest(page, size);
    }


    List<DTOSentenceDisplay> convertKwicsToDisplay(Page<Kwics> kwicData, int pageSize) {
        List<DTOSentenceDisplay> result = new ArrayList<DTOSentenceDisplay>(pageSize);

        long startTime = System.currentTimeMillis();

        for (Kwics kwic : kwicData) {
            KwicSentence kwicSentence = kwic.getKwicSentence();
            int splitIndex = kwic.getPlace();
            result.add(sentenceToDisplay(kwicSentence, splitIndex));
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Converting to Display took: "+elapsedTime);

        return result;
    }

    DTOSentenceDisplay sentenceToDisplay(KwicSentence kwicSentence, int splitIndex){
        int currentSentencePlace = kwicSentence.getSentencePlace();
        String file = kwicSentence.getFileName();
        ArrayList<String>[] fiveBeforeAndAfter = find5BeforeAndAfter(file, currentSentencePlace);
        DTOSentenceDisplay display = new DTOSentenceDisplay(kwicSentence, splitIndex, fiveBeforeAndAfter[0], fiveBeforeAndAfter[1]);
        return display;
    }

    private ArrayList<String>[] find5BeforeAndAfter(String file, int currentSentencePlace) {

        long startTime = System.currentTimeMillis();
        List<KwicSentence> BeforeAndAfter5 = kwicSentenceRepository.findByFileNameAndSentencePlaceBetweenOrderBySentencePlace(file, currentSentencePlace-5, currentSentencePlace+5); //TODO takes ~1000millis
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Querying the DB took: "+elapsedTime);

//        startTime = System.currentTimeMillis();
//        List<KwicSentence> test = kwicSentenceRepository.findByFileNameAndSentencePlaceBetween(file, currentSentencePlace-5, currentSentencePlace+5);
//        stopTime = System.currentTimeMillis();
//        elapsedTime = stopTime - startTime;
//        System.out.println("Querying the D　without sort took: "+elapsedTime);


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

    List<String> find5Between(String file, int from, int to) {
        List<String> five = new ArrayList<String>(5);

        List<KwicSentence> sentencesFB = kwicSentenceRepository.findByFileNameAndSentencePlaceBetweenOrderBySentencePlace(file, from, to);
        for (KwicSentence ks : sentencesFB) {
            five.add(ks.getSentence());
        }
        return five;
    }


    public int getCount() {
        return totalResults;
    }
}