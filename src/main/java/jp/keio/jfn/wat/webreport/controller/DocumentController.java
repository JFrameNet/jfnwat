package jp.keio.jfn.wat.webreport.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.Utils;
import jp.keio.jfn.wat.annotation.AnnotatedSentence;
import jp.keio.jfn.wat.annotation.Tag;
import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.AnnotationSetRepository;
import jp.keio.jfn.wat.repository.CorpusRepository;
import jp.keio.jfn.wat.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * This class is a controller for the document index. It handles operations on DocumentOutput objects.
 */
@ManagedBean
@Controller
@Scope("view")
public class DocumentController implements Serializable {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    CorpusRepository corpusRepository;

    @Autowired
    AnnotationSetRepository annotationSetRepository;

    private String filter = "";

    private List<Document> allDocs = new ArrayList<Document>();

    /**
     * Filters documents when a user types a search string in the input field of the document index.
     * Updates this.allDocs with all the documents whose name or parent corpus name matches the search string.
     */
    public void filterCorpora() {
        List <Document> filteredDocs  = new ArrayList<Document>();
        for (Document doc : documentRepository.findAll()) {
            if (Utils.matchSearch(filter, doc.getCorpus().getName()) || Utils.matchSearch(filter, doc.getName())) {
                filteredDocs.add(doc);
            }
        }
        allDocs = filteredDocs;
    }

    /**
     * Getter for allDocs. If the list if empty and the string search is empty it returns all documents.
     */
    public List<Document> getAllDocs () {
        if (filter.isEmpty() && allDocs.isEmpty()) {
            List <Document> documentList = new ArrayList<Document>();
            for (Document lu : documentRepository.findAll()) {
                documentList.add(lu);
            }
            allDocs = documentList;
        }
        return allDocs;
    }

    public String getTagColor(Tag tag, AnnotatedSentence sentence) {
        if (tag.getFrameElement() == null) {
            return sentence.getBkgColor();
        } else {
            return tag.getColor();
        }
    }

    public void setFilter (String f) {
        filter = f;
    }

    public String getFilter () {
        return filter;
    }

    public void setDocumentRepository(DocumentRepository d) {
        documentRepository = d;
    }

    public void setAllDocs(List<Document> list) {
        allDocs = list;
    }

}
