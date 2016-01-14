package jp.keio.jfn.wat.controller;

import java.util.*;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.*;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.web.bind.annotation.RestController;

@ManagedBean
@RestController
//@Scope("session")
/**
 * Created by jfn on 1/14/16.
 */
public class TabController {
    private List<Frame> currentFrames = new ArrayList<Frame>();
    private List<LexUnit> currentLU = new ArrayList<LexUnit>();

    private LexUnit mainLU;

    private Frame mainFrame;

    @Autowired
    FrameRepository frameRepository;

    @Autowired
    LexUnitRepository lexUnitRepository;

    public String addFrame(String name) {
        Frame frame = frameRepository.findByName(name).get(0);
        for (Frame frame1 : currentFrames) {
            if (frame.getId().equals(frame1.getId())) {
                currentFrames.remove(frame1);
                break;
            }
        }
        currentFrames.add(0,frame);
        mainFrame = frame;
        return "frameOutput?faces-redirect=true&i-0&frame=" + name;
    }

    public String addLU(LexUnit lu) {
        currentLU.add(0,lu);
        mainLU = lu;
        return "lexUnitOutput?faces-redirect=true&i=1&lu=" + lu.getId();
    }

    public void onTabClose(TabCloseEvent event) {
        String name = event.getTab().getTitle();
        Frame closed = frameRepository.findByName(name).get(0);
        for (Frame frame : currentFrames) {
            if (frame.getId().equals(closed.getId())) {
                currentFrames.remove(frame);
                break;
            }
        }
        if (closed.getId().equals(mainFrame.getId())) {
            mainFrame = currentFrames.get(0);
        }
    }

    public void onTabChange(TabChangeEvent event){
        mainFrame = frameRepository.findByName(event.getTab().getTitle()).get(0);
    }

    public void onTabLUClose(TabCloseEvent event) {
        String id = event.getTab().getId().substring(1);
        LexUnit closed = lexUnitRepository.findById(Integer.parseInt(id));
        for (LexUnit lexUnit : currentLU) {
            if (lexUnit.getId() == closed.getId()) {
                currentLU.remove(lexUnit);
                break;
            }
        }
        if (closed.getId() == mainLU.getId()) {
            mainLU = currentLU.get(0);
        }
    }

    public void onTabLUChange(TabChangeEvent event){
        String id = event.getTab().getId().substring(1);
        mainLU = lexUnitRepository.findById(Integer.parseInt(id));
    }

    public String backToIndex() {
        currentFrames = new ArrayList<Frame>();
        mainFrame = null;
        currentLU = new ArrayList<LexUnit>();
        mainLU = null;
        return "index?faces-redirect=true";
    }


    public Frame getMainFrame() {
        return mainFrame;
    }

    public LexUnit getMainLU() {
        return mainLU;
    }

    public void setMainLU(LexUnit mainLU) {
        this.mainLU = mainLU;
    }

    public List<Frame> getCurrentFrames() {
        return currentFrames;
    }

    public void setCurrentFrames(List<Frame> currentFrames) {
        this.currentFrames = currentFrames;
    }

    public List<LexUnit> getCurrentLU() {
        return currentLU;
    }


    public void setCurrentLU(List<LexUnit> currentLU) {
        this.currentLU = currentLU;
    }
}
