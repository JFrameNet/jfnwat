package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.repository.FrameRepository;
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

    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch (String search) {
        this.search = search;
    }

    public String navigateWithSearch() {
        return "test-content?faces-redirect=true&search=" + search;
    }

    public List<Frame> findByName () {
        if (search.isEmpty()) {
            List<Frame> myList = new ArrayList<Frame>();
            for (Frame frame : frameRepository.findAll()) {
                myList.add(frame);
            }
            return myList;
        } else {
            return frameRepository.findByName(search);
        }
    }

}