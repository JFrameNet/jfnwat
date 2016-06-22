package jp.keio.jfn.wat.KWIC;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 *   This class implements the primeface extended lazy data model used by the DataTable in kwicOutput.xhtml
 *
 *   The Connection with the database is left to the kwic transactional class,
 *   because @transactional gave the following problem when used in this class:
 *   - ?
 *
 *   Also the class with the database connection needs to be autowired to autowire the repositories.
 */
@Component
public class LazyKwicData extends LazyDataModel<DTOSentenceDisplay>{

    private KwicTransactions kwicTransactions;

    private Map<String, DTOSentenceDisplay> rowKeyMap;

    private LazyKwicData() {}

    public LazyKwicData(DTOKwicSearch dtoKwicSearch, KwicTransactions kwicTransactions) throws UnknownWordExeption {
        System.out.println("Start");
/**/         long startTime = System.currentTimeMillis();

        this.kwicTransactions = kwicTransactions;
        kwicTransactions.setNewSearch(dtoKwicSearch);
        setRowCount((int) kwicTransactions.getCount());
/**/        long stopTime = System.currentTimeMillis();

/**/         long elapsedTime = stopTime - startTime;
/**/         System.out.println("preparing and counting data took: "+elapsedTime);
    }
    
    @Override
    public List<DTOSentenceDisplay> load(int first, int pageSize,
                                         List<SortMeta> multiSortMeta, Map<String, Object> filters){
/**/         long startTime = System.currentTimeMillis();
/**/

        List<DTOSentenceDisplay> currentPage = kwicTransactions.getData(first, pageSize, multiSortMeta, filters);

/**/        long stopTime = System.currentTimeMillis();
/**/         long elapsedTime = stopTime - startTime;
/**/         System.out.println("load data took: "+elapsedTime);

        setRowCount((int) kwicTransactions.getCount());
        return currentPage;
    }

    @Override
    public DTOSentenceDisplay getRowData(String rowKey){
        return rowKeyMap.get(rowKey);
    }

    @Override
    public Object getRowKey(DTOSentenceDisplay DTOSentenceDisplay){
        rowKeyMap.put(""+DTOSentenceDisplay.getKwicSentence().getId(), DTOSentenceDisplay);
        return  DTOSentenceDisplay.getKwicSentence().getId();
    }
}
