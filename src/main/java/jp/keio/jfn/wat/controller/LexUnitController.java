package jp.keio.jfn.wat.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;

import com.sun.xml.internal.bind.v2.TODO;
import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.AnnotationSetRepository;
import jp.keio.jfn.wat.repository.LexUnitRepository;
import jp.keio.jfn.wat.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@ManagedBean
@RestController
@Scope("view")
public class LexUnitController implements Serializable {
    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    AnnotationSetRepository annotationSetRepository;

    private int lexUnitId;

    private String filter = "";

    private List<LexUnit> orderedLU = new ArrayList<LexUnit>();

    private LexUnit currentLU;

    private List<Sentence> annotatedSentence;

    public String displayLexUnit () {
        currentLU = lexUnitRepository.findById(lexUnitId);
        findSentences();
        return currentLU.getName();
    }

    public List<Status> getStatusForLU (LexUnit lu) {
        Iterable<Status> allStatus = statusRepository.findAll();
        List<Status> myList = new ArrayList<Status>();
        for (Status status : allStatus) {
            if (status.getLexUnit().getId() == lu.getId()) {
                myList.add(status);
            }
        }
        return myList;
    }

    public void orderLU () {
//        List <String> sortedNames = new ArrayList<String>();
//        for (LexUnit lexUnit : lexUnitRepository.findAll()) {
//            sortedNames.add(lexUnit.getName());
//        }
//        Collections.sort(sortedNames);
//        return sortedNames;
        List<LexUnit> allLU= new ArrayList<LexUnit>();
        for (LexUnit lu : lexUnitRepository.findAll()) {
            if (matchSearch(filter, lu.getName())) {
                allLU.add(lu);
            } else if (matchSearch(filter, lu.getFrame().getName())) {
                allLU.add(lu);
            }
        }
        orderedLU = allLU;
    }

    private void findSentences () {
        List<Sentence> allSenteces = new ArrayList<Sentence>();
        for (AnnotationSet annotSet : annotationSetRepository.findAll()) {
            if (annotSet.getLexUnit().getId() == lexUnitId) {
                allSenteces.add(annotSet.getSentence());
            }
        }
        annotatedSentence = allSenteces;
    }
    public int getLexUnitId() {
        return lexUnitId;
    }

    public void setLexUnitId (int id) {
        lexUnitId = id;
    }

    public LexUnit getCurrentLU () {
        return currentLU;
    }

    public List<Sentence> getAnnotatedSentence () {
        return annotatedSentence;
    }

    public void setFilter (String f) {
        filter = f;
    }

    public String getFilter () {
        return filter;
    }

    public void setOrderedLU (List<LexUnit> list) {
        orderedLU = list;
    }

    public List<LexUnit> getOrderedLU () {
        if (orderedLU.isEmpty() && filter.isEmpty()) {
            List <LexUnit> allLu = new ArrayList<LexUnit>();
            for (LexUnit lu : lexUnitRepository.findAll()) {
                allLu.add(lu);
            }
            return allLu;
        } else {
            return orderedLU;
        }
    }

    private boolean matchSearch (String query, String name) {
        return ((name.equalsIgnoreCase(query))
                ||(name.toLowerCase().contains(query.toLowerCase()))
                ||(query.toLowerCase().contains(name.toLowerCase())));
    }

}