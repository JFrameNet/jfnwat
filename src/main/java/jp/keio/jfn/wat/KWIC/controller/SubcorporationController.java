package jp.keio.jfn.wat.KWIC.controller;

import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.AnnotationSetRepository;
import jp.keio.jfn.wat.repository.AnnotationStatusRepository;
import jp.keio.jfn.wat.repository.SentenceRepository;
import jp.keio.jfn.wat.repository.SubCorpusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jfn on 6/28/16.
 */
@ManagedBean
@Controller
@Scope("view")
public class SubcorporationController implements Serializable {
    @Autowired
    SentenceRepository sentenceRepository;

    @Autowired
    SubCorpusRepository subCorpusRepository;

    @Autowired
    AnnotationSetRepository annotationSetRepository;

    @Autowired
    AnnotationStatusRepository annotationStatusRepository;

    private AnnotationStatus annotationStatus;
    private LexUnit selectedLu;
    private List<Sentence> toSubCorporate;
    private SubCorpus subCorpus;

    @PostConstruct
    public void init(){
        annotationSetRepository = annotationSetRepository;
    }


    public void setSelectedLU(LexUnit lu){
        this.selectedLu = lu;
    }

    public LexUnit getSelectedLu() {
        return selectedLu;
    }

    private void findSentencesForSubCorpus(){
        toSubCorporate = new ArrayList<>();
    }

    @Transactional
    public void subcorporate() {
        createSubCorpus();
        createAnnotationSets();

        System.out.println("create a new subcorpus");
    }

    private void createSubCorpus() {
        String name = "Kwic";
        SubCorpus newSupCorpus = new SubCorpus();
        newSupCorpus.setName(name);
        newSupCorpus.setLexUnit(selectedLu);
        subCorpus = newSupCorpus;
        subCorpusRepository.save(subCorpus);
    }

    private void createAnnotationSets() {
        List<AnnotationSet> annotationSets = new ArrayList<>();
        for (Sentence sentence:toSubCorporate) {
            AnnotationSet annotationSet = new AnnotationSet();
            annotationSet.setLexUnit(selectedLu);
            annotationSet.setSubCorpus(subCorpus);
            annotationSet.setSentence(sentence);
            annotationSet.setAnnotationStatus(annotationStatus);
            annotationSets.add(annotationSet);
        }
        annotationSetRepository.save(annotationSets);
    }
}
