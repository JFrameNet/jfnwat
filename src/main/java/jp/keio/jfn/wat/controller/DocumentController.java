package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;

import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.AnnotationSetRepository;
import jp.keio.jfn.wat.repository.CorpusRepository;
import jp.keio.jfn.wat.repository.DocumentRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Returns a SentenceDisplay object with the corresponding list of tags.
     */
    public SentenceDisplay getAnnotation(DocumentOutput doc, SentenceDisplay sentenceDisplay) {
        sentenceDisplay.getAnnotation(doc.getAllFE());
        return sentenceDisplay;
    }

    /**
     * Sets the annotation set for a SentenceDisplay object.
     * If the chosen annotation set is already being displayed, the future annotation set is set to null (hide annotation).
     */
    public void setAnnotationSentence(SentenceDisplay sentence, AnnotationSet annotationSet) {
        AnnotationSet current = sentence.getDisplayedAnnotationSet();
        if ((current != null) && (current.getId() == annotationSet.getId())) {
            sentence.setDisplayedAnnotationSet(null);
        } else {
            sentence.setDisplayedAnnotationSet(annotationSet);
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
