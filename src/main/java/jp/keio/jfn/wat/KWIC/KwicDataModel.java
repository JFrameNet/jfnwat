package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.KWIC.domain.KwicSentence;
import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import jp.keio.jfn.wat.KWIC.domain.Kwics;
import jp.keio.jfn.wat.KWIC.repository.KwicSentenceRepository;
import jp.keio.jfn.wat.KWIC.repository.WordRepository;
import jp.keio.jfn.wat.domain.WordForm;
import jp.keio.jfn.wat.repository.LexemeRepository;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by jfn on 4/12/16.
 */
@Service
@Scope("view")
public class KwicDataModel extends LazyDataModel<KwicSentence> {

    private String search;
    private List<Kwics> dataScource= new ArrayList<Kwics>();
    private String splitRegex;
    private List<String> splitWords;


    @Autowired
    KwicSentenceRepository kwicSentenceRepository;

    @Autowired
    WordRepository wordRepository;

    @Autowired
    LexemeRepository lexemeRepository;


    public KwicDataModel() {}

    public void setSearch(String search) throws NoResultsExeption {
        this.search = search;
        findAllKwicsWithSearch();
    }

    private void findAllKwicsWithSearch() throws NoResultsExeption{
        dataScource = new ArrayList<Kwics>();
        splitWords = new ArrayList<String>();
        boolean results = wordFormKwics();
        try {
            dataScource.addAll(wordRepository.findByWord(search).getKwics());
            splitWords.add(search);
            buildSplitRegEx(splitWords);
            results = true;
        } finally {
            if (!results){
                throw new NoResultsExeption();
            }
        }
    }

    private Boolean wordFormKwics(){
        Boolean bool = false;
        try{
            List<WordForm> wordForms = lexemeRepository.findByName(search).getWordForms();
            for (WordForm wordForm : wordForms) {
                String form = wordForm.getForm();
                KwicWord word = wordRepository.findByWord(form);
                if (word != null) {
                    List<Kwics> kwics = word.getKwics();
                    dataScource.addAll(kwics);
                }
                splitWords.add(form);
            }
            bool = true;
        } finally {
            return bool;
        }
    }

    private void buildSplitRegEx(List<String> wordForms){
        StringBuilder builder = new StringBuilder();
        for (String form : wordForms) {
            builder.append(Pattern.quote(form));
            builder.append('|');
        }
        builder.deleteCharAt(builder.lastIndexOf("|"));
        splitRegex = builder.toString();
    }

    @Override
    public KwicSentence getRowData(String rowKey){
        return kwicSentenceRepository.findById(Integer.parseInt(rowKey));
    }

    @Override
    public Object getRowKey(KwicSentence kwicSentence){
        return kwicSentence.getId();
    }


    @Override
    public List<KwicSentence> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {

        //filter
        List<Kwics> filteredData = filter(dataScource, filters);

        //rowCount
        int dataSize = filteredData.size();
        this.setRowCount(dataSize);

        //sort
        List<Kwics> sortedData = sort(filteredData, sortField, sortOrder);

        //paginate
        List<Kwics> dataPage = paginate(sortedData, dataSize, first, pageSize);

        //Process page for display
        return convertKwicsToSentences(dataPage, pageSize);
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

    private List<Kwics> sort(List<Kwics> filteredData, String sortField, SortOrder sortOrder) {
        /*        if(sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }
*/
        return filteredData;
    }


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


    private List<KwicSentence> convertKwicsToSentences(List<Kwics> kwicData, int pageSize) {
        List<KwicSentence> result = new ArrayList<KwicSentence>(pageSize);

        for (int i = 0, kwicDataSize = kwicData.size(); i < kwicDataSize; i++) {
            Kwics kwic = kwicData.get(i);

            KwicSentence kwicSentence = kwic.getKwicSentence();
            String[] splitSentence = kwicSentence.getSentence().split(splitRegex);

            kwicSentence.beforeSearch = splitSentence[0];
            for (String split: splitWords) {
                if(kwicSentence.getSentence().contains(split)){
                    kwicSentence.search = split;
                }
            }
            try {
                kwicSentence.AfterSearch = splitSentence[1];
            } catch (IndexOutOfBoundsException e){
                kwicSentence.search = "ERROR";
                System.out.println(splitWords + " " +kwicSentence.getSentence());
            }

            result.add(kwicSentence);

        }
        return result;
    }




    public void setKwicSentenceRepository(KwicSentenceRepository kwicSentenceRepository) {
        this.kwicSentenceRepository = kwicSentenceRepository;
    }
}
