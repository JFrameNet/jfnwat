package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;

import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.AnnotationSetRepository;
import jp.keio.jfn.wat.repository.CorpusRepository;
import jp.keio.jfn.wat.repository.DocumentRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Transactional;

@ManagedBean
@Controller
public class DocumentController implements Serializable {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    CorpusRepository corpusRepository;

    @Autowired
    AnnotationSetRepository annotationSetRepository;

    private String filter = "";
    private List<Document> allDocs = new ArrayList<Document>();

    public void setFilter (String f) {
        filter = f;
    }

    public String getFilter () {
        return filter;
    }

    public void filterCorpora() {
        List <Document> filteredDocs  = new ArrayList<Document>();
        for (Document doc : documentRepository.findAll()) {
            if (matchSearch(filter, doc.getCorpus().getName()) || matchSearch(filter, doc.getName())) {
                filteredDocs.add(doc);
            }
        }
        allDocs = filteredDocs;
    }

    private boolean matchSearch (String query, String name) {
        return ((name.equalsIgnoreCase(query))
                ||(name.toLowerCase().contains(query.toLowerCase()))
                ||(query.toLowerCase().contains(name.toLowerCase())));
    }

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

    public SentenceOutput showAnnotatedLU (DocumentOutput doc) {
        if (doc != null) {
            return doc.processSentences();
        } else {
            return  null;
        }

    }


    public List<SentenceOutput> showAnnotation(DocumentOutput doc) {
        return doc.getSelectedSentences();
    }

    public void removeSentence (DocumentOutput doc, SentenceOutput sentenceOutput) {
        doc.getSelectedSentences().remove(sentenceOutput);
    }

    @Transactional
    public void selectLU(DocumentOutput doc, AnnotationSet a) {
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
        SentenceOutput processed = lu.auxFun(annotationSet, allLabels, 75, false);
        for (List<ElementTag> elementTagList : processed.getElements()) {
            for (ElementTag elementTag : elementTagList) {
                String tag = elementTag.getTag();
                if (tag.equals("")) {
                    elementTag.setTagColor("#F5F5F5");
                } else {
                    elementTag.setTagColor(TabController.allColors.get(doc.getAllFE().indexOf(tag)));
                }
            }
        }
        doc.getSelectedSentences().add(processed);
    }
}
