package jp.keio.jfn.wat.controller;

import java.util.*;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.*;
import org.hibernate.Hibernate;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@ManagedBean
@RestController
//@Scope("session")
/**
 * Created by jfn on 1/14/16.
 */
public class TabController {
    private List<FrameOutput> loadedFrames = new ArrayList<FrameOutput>();

    private List<LUOutput> loadedLUs = new ArrayList<LUOutput>();

    private LexUnit mainLU;

    private Frame mainFrame;

    private int index = 0;

    private List<LightLU> lightLUs = new ArrayList<LightLU>();
    private List<String> frameName = new ArrayList<String>();

    @Autowired
    FrameRepository frameRepository;

    @Autowired
    LexUnitRepository lexUnitRepository;

    @Transactional
    public String addFrame(String name) {
        mainFrame = frameRepository.findByName(name).get(0);
        Hibernate.initialize(mainFrame.getLexUnits());
        Hibernate.initialize(mainFrame.getFrameElements());
        Hibernate.initialize(mainFrame.getFrameRelations1());
        for (FrameRelation relation : mainFrame.getFrameRelations1()) {
            Hibernate.initialize(relation.getFerelations());
        }
        Hibernate.initialize(mainFrame.getFrameRelations2());
        loadedFrames.add(new FrameOutput(mainFrame));
        index = loadedFrames.size() -1;
        return "frameOutput?faces-redirect=true&i-0&frame=" + name;
    }

    @Transactional
    public String addLU(LightLU lu) {
        System.out.print("added");
        mainLU = lexUnitRepository.findById(lu.getId());
        Hibernate.initialize(mainLU.getAnnotationSets());
        for (AnnotationSet annotationSet : mainLU.getAnnotationSets()) {
            Hibernate.initialize(annotationSet.getLayers());
            for (Layer layer : annotationSet.getLayers()){
                Hibernate.initialize(layer.getLabels());
            }
        }
        loadedLUs.add(new LUOutput(mainLU));
        index = loadedLUs.size() -1;
        return "lexUnitOutput?faces-redirect=true&i=1&lu=" + lu.getId();
    }

    public void onTabClose(TabCloseEvent event) {
        String name = event.getTab().getTitle();
        for (FrameOutput frame : loadedFrames) {
            if (frame.getName().equals(name)) {
                loadedFrames.remove(frame);
                break;
            }
        }
    }

    public void onTabLUClose(TabCloseEvent event) {
        String id = event.getTab().getTitle();
        for (LUOutput ouput : loadedLUs) {
            if (ouput.getLightLU().getName().equals(id)) {
                loadedLUs.remove(ouput);
                break;
            }
        }
    }

    public String toFrame() {
        clear();
        return "frameOutput?faces-redirect=true&i=0";
    }

    public String toLexUnit() {
        clear();
        return "lexUnitOutput?faces-redirect=true&i=1";
    }

    public String toDocuments() {
        clear();
        return "";
    }

    private void clear() {
        loadedFrames = new ArrayList<FrameOutput>();
        loadedLUs = new ArrayList<LUOutput>();
        mainFrame = null;
        mainLU = null;
    }

    public List<FrameOutput> getLoadedFrames() {
        return loadedFrames;
    }


    public List<LUOutput> getLoadedLUs() {
        return loadedLUs;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<LightLU> getLightLUs() {
        if (lightLUs.isEmpty()) {
            for (LexUnit lu : lexUnitRepository.findAll()) {
                lightLUs.add(new LightLU(lu.getId(), lu.getName(), lu.getFrame().getName()));
            }
        }
        return lightLUs;
    }

    public List<String> getFrameName() {
        if (frameName.isEmpty()) {
            for (Frame frame : frameRepository.findAll()) {
                frameName.add(frame.getName());
            }

        }
        Collections.sort(frameName);
        return frameName;
    }
}
