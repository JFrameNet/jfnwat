package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import java.util.ArrayList;
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

    public SentenceOutput showAnnotatedLU (String screenWidth, DocumentOutput doc) {
        if (doc != null) {
//            double breakline = (float) (Integer.parseInt(screenWidth)  * 0.0625);
            return doc.processSentences(83);
        } else {
            return  null;
        }

    }

    /**
     * Returns all selected annotated sentences for the document.
     */
    public List<SentenceOutput> showAnnotation(DocumentOutput doc) {
        return doc.getSelectedSentences();
    }

    /**
     * Removes the sentence from the list of all sentences selected for the document.
     */
    public void removeSentence (DocumentOutput doc, SentenceOutput sentenceOutput) {
        doc.getSelectedSentences().remove(sentenceOutput);
    }

    /**
     * Creates an annotated sentence depending on the selected target and the screen width.
     * Adds colors to the labels so as to be consistent for the whole document : the same frame element will be of the
     * same color for every sentence of the document.
     */
    @Transactional
    public void selectLU(DocumentOutput doc, AnnotationSet a, String screenWidth) {
        for (SentenceOutput sentenceOutput : doc.getSelectedSentences()) {
            if (a.getId() == sentenceOutput.getAnnotationSet().getId()) {
                return;
            }
        }
        AnnotationSet annotationSet = annotationSetRepository.findById(a.getId());
        Hibernate.initialize(annotationSet.getLayers());
        for (Layer layer : annotationSet.getLayers()){
            Hibernate.initialize(layer.getLabels());
        }
        // Finds all labels from the target layer and the FE layer.
        LUOutput lu = new LUOutput(annotationSet.getLexUnit(), false);
        Layer layerFE = null;
        Layer layerTarget = null;
        for (Layer layer : annotationSet.getLayers()){
            if (layer.getLayerType().getId() == 1) {
                layerFE = layer;
            } else  if (layer.getLayerType().getId() == 2) {
                layerTarget = layer;
            }
            if ((layerFE != null) && (layerTarget != null)) {
                break;
            }
        }
        List<Label> allLabels = layerFE.getLabels();
        allLabels.addAll(layerTarget.getLabels());
//        double breakline = (float) (Integer.parseInt(screenWidth)  * 0.0625);
        SentenceOutput processed = lu.auxFun(annotationSet, allLabels, 80);
        for (List<ElementTag> elementTagList : processed.getElements()) {
            for (ElementTag elementTag : elementTagList) {
                String tag = elementTag.getTag();
                if (tag.equals("")) {
                    elementTag.setTagColor("#F5F5F5");
                } else {
                    elementTag.setTagColor(Utils.allColors.get(doc.getAllFE().indexOf(tag)));
                }
            }
        }
        doc.getSelectedSentences().add(processed);
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
