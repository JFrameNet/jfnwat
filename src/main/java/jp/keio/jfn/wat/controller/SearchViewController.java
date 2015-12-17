package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.domain.LexUnit;
import jp.keio.jfn.wat.domain.Corpus;
import jp.keio.jfn.wat.repository.CorpusRepository;
import jp.keio.jfn.wat.repository.FrameRepository;
import jp.keio.jfn.wat.repository.LexUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
@Controller
public class SearchViewController implements Serializable{

    @Autowired
    FrameRepository frameRepository;

    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    CorpusRepository corpusRepository;

    private String search;

    public List<String> completeText(String query) {
        List<String> results = new ArrayList<String>();
        for (Frame frame : frameRepository.findAll()) {
            if (matchSearch(query, frame.getName())) {
                results.add(frame.getName());
            }
        }
        for (LexUnit lu : lexUnitRepository.findAll()) {
            if (matchSearch(query, lu.getName())) {
                results.add(lu.getName());
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

    public List<LexUnit> findLUKeyword () {
        if (search.isEmpty()) {
            return new ArrayList<LexUnit>();
        } else {
            List<LexUnit> lexUnitList = new ArrayList<LexUnit>();
            for (LexUnit lu : lexUnitRepository.findAll()) {
                if (matchSearch(search, lu.getName())) {
                    lexUnitList.add(lu);
                }
            }
            return lexUnitList;
        }
    }

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
            return corpusList;
        }
    }

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