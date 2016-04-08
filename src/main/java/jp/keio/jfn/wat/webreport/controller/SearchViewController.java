package jp.keio.jfn.wat.webreport.controller;


import jp.keio.jfn.wat.Utils;
import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.webreport.LightLU;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Scope;

import jp.keio.jfn.wat.repository.CorpusRepository;
import jp.keio.jfn.wat.repository.DocumentRepository;
import jp.keio.jfn.wat.repository.FrameRepository;
import jp.keio.jfn.wat.repository.LexUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.ManagedBean;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a controller for the home and the search page.
 */
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

    /**
     * Filter for the autocomplete search bar. If a frame name, a corpus name, a document name or a lexical unit name
     * matches the string entered by the user, it is added to the list of possible results to be displayed.
     */
    public List<String> completeText(String query) {
        List<String> results = new ArrayList<String>();
        for (LexUnit lu : lexUnitRepository.findAll()) {
            if (Utils.matchSearch(query, lu.getName())) {
                results.add(lu.getName());
            }
        }
        for (Frame frame : frameRepository.findAll()) {
            if (Utils.matchSearch(query, frame.getName())) {
                results.add(frame.getName());
            }
        }
        for (Corpus cp : corpusRepository.findAll()) {
            if (Utils.matchSearch(query, cp.getName())) {
                results.add(cp.getName());
            }
        }
        for (Document document : documentRepository.findAll()) {
            if (Utils.matchSearch(query, document.getName())) {
                results.add(document.getName());
            }
        }
        return results;
    }

    public String navigateWithSearch() {
        return "searchPage?faces-redirect=true&search=" + search;
    }

    /**
     * Finds all the frame whose name match the search filter
     *
     * @return a list of frames
     */
    public List<Frame> findFrameKeyword () {
        if (search.isEmpty()) {
            return new ArrayList<Frame>();
        } else {
            List<Frame> frameList = new ArrayList<Frame>();
            for (Frame frame : frameRepository.findAll()) {
                if (Utils.matchSearch(search, frame.getName())) {
                    frameList.add(frame);
                }
            }
            return frameList;
        }
    }

    /**
     * Finds all the lexical units whose name or frame name match the search filter
     *
     * @return a list of LightLU objects created from a list of the selected lexical units.
     */
    @Transactional
    public List<LightLU> findLUKeyword () {
        if (search.isEmpty()) {
            return new ArrayList<LightLU>();
        } else {
            List<LightLU> lexUnitList = new ArrayList<LightLU>();
            for (LexUnit lu : lexUnitRepository.findAll()) {
                if ((Utils.matchSearch(search, lu.getName())) || (Utils.matchSearch(search, lu.getFrame().getName()))) {
                    LightLU lightLU = new LightLU(lu.getId(), lu.getName(), lu.getFrame().getName());
                    try {
                        Hibernate.initialize(lu.getStatuses());
                        lightLU.setStatuses(lu.getStatuses());
                    } catch (Exception e) {
                        System.err.print(e.toString());
                    }
                    lexUnitList.add(lightLU);
                }
            }
            return lexUnitList;
        }
    }

    /**
     * Finds all the corpora whose name or child document name match the search filter
     *
     * @return a list of corpora
     */
    public List<Corpus> findCorpusKeyword () {
        if (search.isEmpty()) {
            return new ArrayList<Corpus>();
        } else {
            List<Corpus> corpusList = new ArrayList<Corpus>();
            for (Corpus cp : corpusRepository.findAll()) {
                if (Utils.matchSearch(search, cp.getName())) {
                    corpusList.add(cp);
                }
            }
            for (Document document : documentRepository.findAll()) {
                if (Utils.matchSearch(search, document.getName())) {
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

    public String getSearch() {
        return search;
    }

    public void setSearch (String search) {
        this.search = search;
    }

    public void setCorpusRepository(CorpusRepository corpusRepository) {
        this.corpusRepository = corpusRepository;
    }

    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void setFrameRepository(FrameRepository frameRepository) {
        this.frameRepository = frameRepository;
    }

    public void setLexUnitRepository(LexUnitRepository lexUnitRepository) {
        this.lexUnitRepository = lexUnitRepository;
    }
}