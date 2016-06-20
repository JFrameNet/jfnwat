package jp.keio.jfn.wat.KWIC;

import java.io.*;
import java.util.stream.Stream;

/**
 * Created by jfn on 6/14/16.
 */
public class StreamedKwicData {
    static ObjectOutputStream oos;

    private KwicTransactions kwicTransactions;

    private StreamedKwicData(){

    }

    public StreamedKwicData(DTOKwicSearch dtoKwicSearch, KwicTransactions kwicTransactions) throws UnknownWordExeption{
        this.kwicTransactions = kwicTransactions;
        kwicTransactions.setNewSearch(dtoKwicSearch);
    }


    public InputStream getStream() throws IOException{
        return kwicTransactions.stream();
    }
}
