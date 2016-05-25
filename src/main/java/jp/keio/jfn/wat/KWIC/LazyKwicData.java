package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.KWIC.domain.Kwics;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * Created by jfn on 4/12/16.
 */
@Component
public class LazyKwicData extends LazyDataModel<DTOSentenceDisplay>{

    private KwicTransactions kwicTransactions;

    private Map<String, DTOSentenceDisplay> rowKeyMap;

    private LazyKwicData() {}

    public LazyKwicData(DTOKwicSearch dtoKwicSearch, KwicTransactions kwicTransactions) throws NoResultsExeption {
        this.kwicTransactions = kwicTransactions;
        kwicTransactions.setNewSearch(dtoKwicSearch);
        setRowCount(kwicTransactions.getCount());
    }



    @Override
    public List<DTOSentenceDisplay> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        long startTime = System.currentTimeMillis();
        System.out.println("Start");

        List<DTOSentenceDisplay> currentPage = kwicTransactions.getData(first, pageSize, sortField, sortOrder, filters);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("load data took: "+elapsedTime);

        setRowCount(kwicTransactions.getCount());
        return currentPage;
    }



    @Override
    public DTOSentenceDisplay getRowData(String rowKey){
        return rowKeyMap.get(rowKey);
    }

    @Override
    public Object getRowKey(DTOSentenceDisplay DTOSentenceDisplay){
        return  DTOSentenceDisplay.kwicSentence.getId();
    }



    private List<Kwics> filter(List<Kwics> data, Map<String, Object> filters) {
        List<Kwics> filtered = new ArrayList<Kwics>();
        /*       for(KwicSentence sentence : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(sentence.getClass().getField(filterProperty).get(sentence));

                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        }
                        else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }

            if(match) {
                data.add(sentence);
            }
        }


        */
        filtered.addAll(data);
        return filtered;
    }



    private List<Kwics> sort(List<Kwics> filteredData, String sortField, SortOrder sortOrder) {
        /*        if(sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }
*/
        return filteredData;
    }

/*
    private List<Kwics> paginate(List<Kwics> data, int dataSize, int first, int pageSize){
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
*/
/*

        private List<Kwics> matchKwicsWithOtherCriteria( List<Kwics> list) {
        if(!colloquial.isEmpty()) {
            KwicWord coWord = wordRepository.findByWord(colloquial);
            if (end) {
                return keepEndOnly(list);
            } else {
                List<Kwics> coList = coWord.getKwics();
                list.retainAll(coList);
                return list;
            }
        }else if(end) {
            return keepEndOnly(list);
        } else {
            return list;
        }
    }
    private List<Kwics> keepEndOnly(List<Kwics> all){
        List<Kwics> ends = new ArrayList<Kwics>();
        for(Kwics kwic: all){
            int place = kwic.getPlace();
            int count = kwicsRepository.countByKwicSentenceAndPlace(kwic.getSentenceID(), place++);
            if(count == 0) ends.add(kwic);
        }
        return ends;
    }

     */

    /*
    private void buildSplitRegEx(List<String> wordForms){
        StringBuilder builder = new StringBuilder();
        for (String form : wordForms) {
            builder.append(Pattern.quote(form));
            builder.append('|');
        }
        builder.deleteCharAt(builder.lastIndexOf("|"));
        splitRegex = builder.toString();
    }
    */

/*
    private void wordFormKwics() throws NoResultsExeption{
        Boolean results = false;
        try{
            List<WordForm> wordForms = lexemeRepository.findByName(search).getWordForms();
            for (WordForm wordForm : wordForms) {
                String form = wordForm.getForm();
                KwicWord word = wordRepository.findByWord(form);
                if (word != null) {
                    List<Kwics> kwics = word.getKwics();
                    dataScource.addAll(kwics);
                }
                stringWordForms.add(form);
            }
            results = true;
        } finally {
            if (!results){
                throw new NoResultsExeption();
            }
        }
    }
*/


}
