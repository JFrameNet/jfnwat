package jp.keio.jfn.wat.KWIC.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import jp.keio.jfn.wat.KWIC.*;
import jp.keio.jfn.wat.KWIC.viewelements.KwicDataView;
import jp.keio.jfn.wat.Utils;
import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.repository.FrameRepository;
import jp.keio.jfn.wat.webreport.FrameOutput;
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

    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    KwicDataView dataView;

    @Autowired
    KwicTransactions kwicTransactions;

    @Autowired
    FrameController frameController;

    private final int DEFAULT_PAGESIZE = 100;

    // Form variables used in kwicSearch.xhtml
    private String search; // TODO need to remove unwanted spaces and other unwanted input?
    private String collocate;
    private Boolean end;
    private int preScope = 5;
    private int postScope = 5;
    private int endScope = 5;
    private List<String> corpora = Arrays.asList("BK", "OW", "OM", "OC");

    private boolean random;
    private int randomNumber = 100;
    private String regex;

    // Output variables
    private LazyDataModel<DTOSentenceDisplay> lazyKwicData;
    private List<Frame> relevantFrames = new ArrayList<Frame>();
    private int pageSize = DEFAULT_PAGESIZE;
    private boolean kwicView = true;
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
    public void setSearch(String search) {
        this.search = search;
    }
    public String getSearch() {return search;}

    public void setCollocate(String collocate) {
        this.collocate = collocate;
    }
    public String getCollocate() {
        return collocate;
    }

    public void setEnd(Boolean end) {
        this.end = end;
    }
    public Boolean getEnd() {
        return end;
    }

    public void setPreScope(int preScope) {
        this.preScope = preScope;
    }
    public int getPreScope() {
        return preScope;
    }

    public void setPostScope(int postScope) {
        this.postScope = postScope;
    }
    public int getPostScope() {
        return postScope;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    // Settings defined in kwicSearch.xhtml
    public void setCorpora(List<String> corpora) {
        this.corpora = corpora;
    }
    public List<String> getCorpora() {
        return corpora;
    }

    public void setRandom(boolean random) {
        this.random = random;
        pageSize = (random)?  randomNumber : DEFAULT_PAGESIZE;
    }
    public boolean getRandom() {
        return random;
    }

    public void setRandomNumber(int randomNumber) {
        this.randomNumber = randomNumber;
        pageSize = (random)?  randomNumber : DEFAULT_PAGESIZE;
    }
    public int getRandomNumber() {
        return randomNumber;
    }

    public void setEndScope(int endScope) {
        this.endScope = endScope;
    }
    public int getEndScope() {
        return endScope;
    }

    public void setCharNum(int charNum) {
        this.charNum = charNum;
        kwicTransactions.setCONTEXT_SCOPE(charNum);
    }
    public int getCharNum() {
        return this.charNum;
    }






    public void matchLazyDataWithSearch() {
        try {
            if(search != null){
                lazyKwicData = new LazyKwicData(new DTOKwicSearch(search, collocate, preScope, postScope, end, endScope, corpora, random), kwicTransactions);
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
        return search == null ? 20 : search.length()*20; // TODO longest word form
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setKwicView(boolean kwicView) {
        this.kwicView = kwicView;
    }
    public boolean getKwicView() {
        return kwicView;
    }
    public boolean getNonKwicView() {
        return !kwicView;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public Object getSort() {
        return sort;
    }



    /*adjusted from http://stackoverflow.com/questions/2914025/ */
    public void download() throws IOException {
        String fileName = "jfnkwic_"+search+".txt";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
        ec.setResponseContentType("text/plain"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
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


            fc.responseComplete(); // Important! Else JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
    }

    private InputStream getDatAsInputStream() {
        matchLazyDataWithSearch();
        List<DTOSentenceDisplay> curentPageList = kwicTransactions.curentPageList;
        return (InputStream) curentPageList.stream(); //TODO curentPageList = NULL
        /*
            @Query("select u from User u")
            Stream<User> findAllByCustomQueryAndStream();

            Stream<User> readAllByFirstnameNotNull();

            @Query("select u from User u")
            Stream<User> streamAllPaged(Pageable pageable);
         */
    }

    /*
    public void documentLink(String fileName){
        Document document = documentRepository.findByName(fileName);
        tabController.addDocRedirect(document);
    }
    */
}

