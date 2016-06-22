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
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    private KwicWord dot;

    private DTOKwicSearch searchIn;
    private List<KwicWord> kwicWordForms = new ArrayList<KwicWord>();
    private List<KwicWord> kwicCollocateForms = new ArrayList<KwicWord>();
    private long totalResults;
    private List<Kwics> currentSearchData;
    private List<Kwics> currentRandomData;
    private Comparator<DTOSentenceDisplay> currentSorter;
    private DTOKwicSearch currentDataSearch;
    private DTOKwicSearch currentRandomSearch;

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
        searchIn.dot = this.dot;
        clear();
        MakeAllFindable();
        totalResults = kwicsRepository.countSearchResults(searchIn);
         //TODO is done 2x, inefficient
    }

    private void clear() {
        kwicWordForms.clear();
        kwicCollocateForms.clear();
    }

    private void MakeAllFindable() throws UnknownWordExeption {
        findAllKwicWords(searchIn.word, kwicWordForms);
        if (kwicWordForms.isEmpty()){throw new UnknownWordExeption(UnknownWordExeption.Cause.KEYWORD);}
        searchIn.words = kwicWordForms;

        if (searchIn.hasCollocate) {
            findAllKwicWords(searchIn.collocate, kwicCollocateForms);
            if(kwicCollocateForms.isEmpty()){ throw new UnknownWordExeption(UnknownWordExeption.Cause.COLLOCATE);}
            searchIn.collocates = kwicCollocateForms;
        }
    }

    private void findAllKwicWords(String search, List<KwicWord> kwicForms){
        try {
            // in some cases more then one result eg　車　(名詞 and 接尾辞 part of speach)
//            List<WordForm> wordForms = getWordForms(search);
//            for (WordForm wordForm : wordForms) {
//                findKwicWords(wordForm.getForm(), kwicForms);
//            }
            findKwicWords(search, kwicForms);
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

    private void findKwicWords(String word, List<KwicWord> kwicForms) {
        List<KwicWord> kwicWords = wordRepository.findByWord(word);
        kwicForms.addAll(kwicWords);
    }


// ********************************************************************************************************************
    public List<DTOSentenceDisplay> getData(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        boolean isRandomSearch = searchIn.random;
        if(isRandomSearch){
            totalResults = pageSize;
            if(searchIn != currentRandomSearch){
                currentRandomData = kwicsRepository.findNRandom(searchIn);
                currentRandomSearch = searchIn;
            }
            return sortPaginateTransform(currentRandomData, first, pageSize, multiSortMeta);
        } else {
/**/long startTime = System.currentTimeMillis();
            if(searchIn != currentDataSearch){
                currentSearchData = kwicsRepository.readAll(searchIn);
                currentDataSearch = searchIn;
            }
/**/long stopTime = System.currentTimeMillis();
/**/long elapsedTime = stopTime - startTime;
/**/System.out.println("DB query took: " + elapsedTime);
            return sortPaginateTransform(currentSearchData, first, pageSize, multiSortMeta);//TODO prevent resort for view change
        }
    }

    private List<DTOSentenceDisplay> sortPaginateTransform(List<Kwics> list, int first, int pageSize, List<SortMeta> multiSortMeta) {
/**/long startTime = System.currentTimeMillis();

        currentSorter = getSorters(multiSortMeta);
        Stream<Kwics> stream = list.stream();
        List<DTOSentenceDisplay> streamedPage;
        streamedPage = stream.
                map(this::kwicsToSort).
                sorted(currentSorter).
                skip(first).limit(pageSize).
                map(this::set5).
                collect(Collectors.toCollection(ArrayList::new));

/**/long stopTime = System.currentTimeMillis();
/**/long elapsedTime = stopTime - startTime;
/**/System.out.println("sort and transform took: " + elapsedTime);

        return streamedPage;
    }

    private Comparator<DTOSentenceDisplay> getSorters(List<SortMeta> multiSortMeta) {
        List<Comparator<DTOSentenceDisplay>> comperators = new ArrayList<>();
        multiSortMeta.sort(DisplaySorter.SORT_PRESEDENSE);
        for (SortMeta sortMeta : multiSortMeta) {
            comperators.add(getSorter(sortMeta.getSortField(), sortMeta.getSortOrder()));
        }
        return new DisplaySorter(comperators);
    }

    private Comparator<DTOSentenceDisplay> getSorter(String sortField, SortOrder sortOrder) {
        Comparator<DTOSentenceDisplay> sorter;
        if(sortField == null ){return  DisplaySorter.UN_SORTED;}

        switch (sortField){
            case "preContext": sorter =  DisplaySorter.BY_PRE_CONTEXT; break;
            case "postContext": sorter =  DisplaySorter.BY_POST_CONTEXT; break;
            default: sorter = DisplaySorter.BY_KEY_WORD;
        }
        return (sortOrder == SortOrder.DESCENDING)? Collections.reverseOrder(sorter) : sorter;
    }

    private List<Kwics> pageToList(Page<Kwics> page) {
        totalResults = (int) page.getTotalElements();
        return page.getContent();
    }

    private Pageable makePagable(int first, int size){
        int page = first/size;
        return new PageRequest(page, size);
    }

    public long getCount() {
        return totalResults;
    }

// ********************************************************************************************************************

    public void setCONTEXT_SCOPE(int CONTEXT_SCOPE) {
        this.CONTEXT_SCOPE = CONTEXT_SCOPE;
    }
    public int getCONTEXT_SCOPE() {return this.CONTEXT_SCOPE; }


    List<DTOSentenceDisplay> convertAllKwicsToDisplay(List<Kwics> kwicData) {
        List<DTOSentenceDisplay> result = new ArrayList<DTOSentenceDisplay>(kwicData.size());

/**/         long startTime = System.currentTimeMillis();

        for (Kwics kwic : kwicData) {
            DTOSentenceDisplay display = aKwicToDisplay(kwic);
            result.add(display);
        }

/**/         long stopTime = System.currentTimeMillis();
/**/         long elapsedTime = stopTime - startTime;
/**/         System.out.println("Converting to Display took: "+elapsedTime);

        return result;
    }

    private DTOSentenceDisplay aKwicToDisplay(Kwics kwic) {
        KwicSentence kwicSentence = kwic.getKwicSentence();
        int keyWordIndex = kwic.getPlace();
        List<KwicSentence> BeforeAndAfter5 =find5BeforeAndAfter(kwicSentence);
        ArrayList<String>[] fiveBeforeAndAfter = separateBeforeAndAfter(kwicSentence.getSentencePlace(), BeforeAndAfter5);
        return new DTOSentenceDisplay(CONTEXT_SCOPE, kwicSentence, keyWordIndex, fiveBeforeAndAfter[0], fiveBeforeAndAfter[1]);
    }

    private List<KwicSentence> find5BeforeAndAfter(KwicSentence kwicSentence) {
        int currentSentencePlace = kwicSentence.getSentencePlace();
        String corpus = kwicSentence.getCorpusName();
        String file = kwicSentence.getFileName();
        return kwicSentenceRepository.findByCorpusNameAndFileNameAndSentencePlaceBetweenOrderBySentencePlace(corpus, file, currentSentencePlace-5, currentSentencePlace+5);
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

    private DTOSentenceDisplay kwicsToSort(Kwics kwic){
        KwicSentence kwicSentence = kwic.getKwicSentence();
        int keyWordIndex = kwic.getPlace();
        return new DTOSentenceDisplay(CONTEXT_SCOPE, kwicSentence, keyWordIndex);
    }

    private DTOSentenceDisplay set5(DTOSentenceDisplay display){
        KwicSentence kwicSentence = display.getKwicSentence();
        List<KwicSentence> BeforeAndAfter5 = find5BeforeAndAfter(kwicSentence);
        ArrayList<String>[] fiveBeforeAndAfter = separateBeforeAndAfter(kwicSentence.getSentencePlace(), BeforeAndAfter5);
        display.setBefore5(fiveBeforeAndAfter[0]);
        display.setAfter5(fiveBeforeAndAfter[1]);
        return display;
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

// ********************************************************************************************************************
    static BufferedOutputStream bos;

    @Transactional
    public InputStream stream() throws IOException {
            List<Kwics> kwicsData = kwicsRepository.readAll(searchIn);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bos = new BufferedOutputStream(baos);


        try {
            currentSearchData.stream().
                map(this::kwicsToSort).
                sorted(currentSorter).
                map(this::set5).
                forEach(d -> d.write(bos));
        } catch (Exception e) {
            e.printStackTrace();
        }

        bos.flush();
            bos.close();

            ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
            return is;
    }
}

/*
    public DTOSentenceDisplay getKwicSentenceByRowKey(String rowKey){
        KwicSentence kwicSentence = kwicSentenceRepository.findById(Integer.parseInt(rowKey));
        return sentenceToDisplay(kwicSentence);
    }
*/
