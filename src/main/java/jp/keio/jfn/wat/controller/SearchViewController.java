package jp.keio.jfn.wat.controller;


import jp.keio.jfn.wat.domain.*;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Scope;

import jp.keio.jfn.wat.repository.CorpusRepository;
import jp.keio.jfn.wat.repository.DocumentRepository;
import jp.keio.jfn.wat.repository.FrameRepository;
import jp.keio.jfn.wat.repository.LexUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;


import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RestController
@Scope("view")
public class SearchViewController implements Serializable{

    @Autowired
    FrameRepository frameRepository;

    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    CorpusRepository corpusRepository;

    @Autowired
    DocumentRepository documentRepository;

    private String search = "";

    public List<String> completeText(String query) {
        List<String> results = new ArrayList<String>();
        for (LexUnit lu : lexUnitRepository.findAll()) {
            if (matchSearch(query, lu.getName())) {
                results.add(lu.getName());
            }
        }
        for (Frame frame : frameRepository.findAll()) {
            if (matchSearch(query, frame.getName())) {
                results.add(frame.getName());
            }
        }
        for (Corpus cp : corpusRepository.findAll()) {
            if (matchSearch(query, cp.getName())) {
                results.add(cp.getName());
            }
        }
        return results;
    }

    public String navigateWithSearch() {
        return "searchPage?faces-redirect=true&search=" + search;
    }

    public List<Frame> findFrameKeyword () {
        if (search.isEmpty()) {
            return new ArrayList<Frame>();
        } else {
            List<Frame> frameList = new ArrayList<Frame>();
            for (Frame frame : frameRepository.findAll()) {
                if (matchSearch(search, frame.getName())) {
                    frameList.add(frame);
                }
            }
            return frameList;
        }
    }

    @Transactional
    public List<LightLU> findLUKeyword () {
        if (search.isEmpty()) {
            return new ArrayList<LightLU>();
        } else {
            List<LightLU> lexUnitList = new ArrayList<LightLU>();
            for (LexUnit lu : lexUnitRepository.findAll()) {
                if ((matchSearch(search, lu.getName())) || (matchSearch(search, lu.getFrame().getName()))) {
                    Hibernate.initialize(lu.getStatuses());
                    LightLU lightLU = new LightLU(lu.getId(), lu.getName(), lu.getFrame().getName());
                    lightLU.setStatuses(lu.getStatuses());
                    lexUnitList.add(lightLU);

                }
            }
            return lexUnitList;
        }
    }

    @Transactional
    public List<Corpus> findCorpusKeyword () {
        if (search.isEmpty()) {
            return new ArrayList<Corpus>();
        } else {
            List<Corpus> corpusList = new ArrayList<Corpus>();
            for (Corpus cp : corpusRepository.findAll()) {
                if (matchSearch(search, cp.getName())) {
                    corpusList.add(cp);
                }
            }
            for (Document document : documentRepository.findAll()) {
                if (matchSearch(search, document.getName())) {
                    boolean in = false;
                    for (Corpus corpus : corpusList) {
                        if (corpus.getId() == document.getCorpus().getId()) {
                            in = true;
                            break;
                        }
                    }
                    if  (!in) {
                        corpusList.add(document.getCorpus());
                    }
                }
            }
            return corpusList;
        }
    }

//    public TreeNode getDocTree () {
//        return createTree(findCorpusKeyword());
//    }

//    private TreeNode createTree(List<Corpus> corpusList) {
//        TreeNode docRoot = new DefaultTreeNode("Root", null);
//        if (corpusList.isEmpty()) {
//            TreeNode empty = new DefaultTreeNode("empty", "empty tree", docRoot);
//            docRoot.getChildren().add(empty);
//        } else {
//            for (Corpus cp : corpusList) {
//                TreeNode node = new DefaultTreeNode("head",cp.getName(), docRoot);
//                for (Document doc : cp.getDocuments()) {
//                    node.getChildren().add(new DefaultTreeNode(doc.getName()));
//                }
//            }
//        }
//        return docRoot;
//    }

    private boolean matchSearch (String query, String name) {
        return ((name.equalsIgnoreCase(query))
                ||(name.toLowerCase().contains(query.toLowerCase()))
                ||(query.toLowerCase().contains(name.toLowerCase())));
    }

    public String getSearch() {
        return search;
    }

    public void setSearch (String search) {
        this.search = search;
    }

}