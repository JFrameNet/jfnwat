package jp.keio.jfn.wat.KWIC.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import jp.keio.jfn.wat.KWIC.*;
import jp.keio.jfn.wat.KWIC.viewelements.KwicDataView;
import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.webreport.controller.FrameController;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import jp.keio.jfn.wat.domain.LexUnit;
import jp.keio.jfn.wat.repository.LexUnitRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Quiri on 3-2-2016.
 */

@ManagedBean
@Controller
@Scope("view")
public class KwicController implements Serializable {

    private final int DEFAULT_PRE_SCOPE = 5;
    private final int DEFAULT_POST_SCOPE = 5;
    private final int DEFAULT_END_SCOPE = 5;
    private final int DEFAULT_N_RANDOM = 100;

    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    KwicDataView dataView;

    @Autowired
    KwicTransactions kwicTransactions;

    @Autowired
    FrameController frameController;


    // Form variables used in kwicSearch.xhtml
    private String keyWord; // TODO need to remove unwanted spaces and other unwanted input?
    private String collocate;
    private int preScope = DEFAULT_PRE_SCOPE;
    private int postScope = DEFAULT_POST_SCOPE;
    private Boolean end;
    private int endScope = DEFAULT_END_SCOPE;
    private List<String> corpora = Arrays.asList("BK", "OW", "OM", "OC");

    private boolean random;
    private int randomNumber = DEFAULT_N_RANDOM;
    private String regex;

    // Output variables
    private DTOKwicSearch search;
    private LazyDataModel<DTOSentenceDisplay> lazyKwicData;
    private List<Frame> relevantFrames = new ArrayList<Frame>();
    private Object sort = "keyWord";
    private int charNum = 20;


    // Navigation to the kwic page
    public String toKwic() { return "concordancer/kwic?faces-redirect=true";}


    public List<String> completeText(String query) { //TODO doesnt get called to first time after pageload

        List<String> results = new ArrayList<String>();

        while(results.size()<=5) {// TODO make 5 a parameter linked to the 5 in max results in kwicform.xhtml
            for (LexUnit lu : lexUnitRepository.findByNameStartingWith(query)) {
                String word = lu.getName().split("\\.")[0];
                if(!results.contains(word)){ results.add(word);}
            }
            break;
        }
        return results;
    }

    // Form getters and setters for kwicSearch.xhtml
    public void setKeyWord(String keyWord) {
        search = new DTOKwicSearch(keyWord);
        this.keyWord = keyWord;
    }
    public String getKeyWord() {return keyWord;}

    public void setEnd(Boolean end) {
        search.end = end;
        this.end = end;
    }
    public Boolean getEnd() {
        return end;
    }

    public void setEndScope(int endScope) {
        search.END_SCOPE = endScope;
        this.endScope = endScope;
    }
    public int getEndScope() {
        return endScope;
    }

    public void setCollocate(String collocate) {
        search.hasCollocate = !collocate.isEmpty();
        search.collocate = collocate;
        this.collocate = collocate;
    }
    public String getCollocate() {
        return collocate;
    }


    public void setPreScope(int preScope) {
        search.PRE_COLLOCATE = preScope;
        this.preScope = preScope;
    }
    public int getPreScope() {
        return preScope;
    }

    public void setPostScope(int postScope) {
        search.POST_COLLOCATE = postScope;
        this.postScope = postScope;
    }
    public int getPostScope() {
        return postScope;
    }

    public void setCorpora(List<String> corpora) {
        search.corpora = corpora;
        this.corpora = corpora;
    }
    public List<String> getCorpora() {
        return corpora;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public void setRandom(boolean random) {
        search.random = random;
        this.random = random;
    }
    public boolean getRandom() {
        return random;
    }

    public void setRandomNumber(int randomNumber) {
        search.randomNumber = randomNumber;
        this.randomNumber = randomNumber;
        if (random){
            dataView.setPageSize(randomNumber);
        } else {
            dataView.resetPageSize();
        }
    }

    public int getRandomNumber() {
        return randomNumber;
    }


    // Display Settings getters and setters
    public void setKwicView(boolean kwicView) {
        dataView.setKwicView(kwicView);
    }

    public boolean getKwicView(){
        return dataView.isKwicView();
    }

    public void setCharNum(int charNum) {
        kwicTransactions.setCONTEXT_SCOPE(charNum);
    }
    public int getCharNum() {
        return kwicTransactions.getCONTEXT_SCOPE();
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public Object getSort() {
        return sort;
    }


    public void matchLazyDataWithSearch() {
        try {
            if(keyWord != null){
                lazyKwicData = new LazyKwicData(search, kwicTransactions);
                relevantFrames = kwicTransactions.findRelevantFrames();
            }
        } catch (Exception e) {
            if(e.getClass() == UnknownWordExeption.class){
                relevantFrames.clear();
            } else {
                e.printStackTrace();
            }
        }
    }

    public LazyDataModel<DTOSentenceDisplay> getLazyData(){
        if(lazyKwicData == null){
            matchLazyDataWithSearch();
        }
        return lazyKwicData;
    }

    public List getRelevantFrames() {
        return relevantFrames;
    }



    // Determines the with of the Key Word column in the view according to the length of the key word that was searched
    public int getWordColumnSize() {
        return keyWord == null ? 20 : keyWord.length()*20; // TODO longest word form
    }



    /*adjusted from http://stackoverflow.com/questions/2914025/ */
    public void download() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();

        if(keyWord != null) {
            String fileName = "jfnwat_kwic.doc";
            ExternalContext ec = fc.getExternalContext();

            ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
            ec.setResponseContentType("application/msword"); // text/plain Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
//        ec.setResponseContentLength(contentLength); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
            ec.setResponseHeader("Content-disposition", "attachment; filename=\"" + fileName + "\""); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.

            BufferedInputStream input = null;
            BufferedOutputStream output = null;

            try {
                input = new BufferedInputStream(getDatAsInputStream());
                output = new BufferedOutputStream(ec.getResponseOutputStream());

                byte[] buffer = new byte[10240];
                for (int length; (length = input.read(buffer)) > 0; ) {
                    output.write(buffer, 0, length);
                }
            } finally {
                output.close();
                input.close();
            }
        }
            fc.responseComplete(); // Important! Else JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
            //TODO empty download results in blank screen
    }

    private InputStream getDatAsInputStream() throws IOException{
        StreamedKwicData streamedKwicData = null;
        try {
            streamedKwicData = new StreamedKwicData(search, kwicTransactions);
        } catch (UnknownWordExeption unknownWordExeption) {
            unknownWordExeption.printStackTrace();
        }
        return streamedKwicData.getStream();
    }



    /*
    public void documentLink(String fileName){
        Document document = documentRepository.findByName(fileName);
        tabController.addDocRedirect(document);
    }
    */
}

