package jp.keio.jfn.wat.controller;

import java.util.*;
import javax.annotation.PostConstruct;
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

    private List<DocumentOutput> loadedDocs = new ArrayList<DocumentOutput>();

    private List<LightLU> orderedLU = new ArrayList<LightLU>();

    private LexUnit mainLU;

    private Frame mainFrame;

    private Document mainDocument;

    private int index = 0;

    public static List<String> allColors = new ArrayList<String>();

    @PostConstruct
    void initColors () {
        List<String> colors = new ArrayList<String>();
        colors.add("#4db6ac");
        colors.add("#F7941E");
        colors.add("#EA5753");
        colors.add("#EF9A9A");
        colors.add("#F7D100");
        colors.add("darkblue");
        colors.add("darkmagenta");
        colors.add("peru");
        colors.add("sandybrown");
        colors.add("steelblue");
        colors.add("tomato");
        colors.add("plum");
        colors.add("olive");
        colors.add("darkgoldenrod");
        colors.add("firebrick");
        allColors = colors;
    }

    private List<LightLU> lightLUs = new ArrayList<LightLU>();
    private List<String> frameName = new ArrayList<String>();

    @Autowired
    FrameRepository frameRepository;

    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Transactional
    public String addFrame(String name) {
        for (FrameOutput frameOutput : loadedFrames) {
            if (frameOutput.getName().equals(name)) {
                index = loadedFrames.indexOf(frameOutput);
                return "frameOutput?faces-redirect=true&i-0&frame=" + name;
            }
        }
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
        for (LUOutput luOutput : loadedLUs) {
            if (luOutput.getLightLU().getName().equals(lu.getName())) {
                index = loadedLUs.indexOf(luOutput);
                return "lexUnitOutput?faces-redirect=true&i=1&lu=" + lu.getId();
            }
        }
        mainLU = lexUnitRepository.findById(lu.getId());
        Hibernate.initialize(mainLU.getAnnotationSets());
        for (AnnotationSet annotationSet : mainLU.getAnnotationSets()) {
            Hibernate.initialize(annotationSet.getLayers());
            for (Layer layer : annotationSet.getLayers()){
                Hibernate.initialize(layer.getLabels());
            }
        }
        loadedLUs.add(new LUOutput(mainLU, true));
        index = loadedLUs.size() -1;
        return "lexUnitOutput?faces-redirect=true&i=1&lu=" + lu.getId();
    }

    @Transactional
    public String addDoc(Document document) {
        for (DocumentOutput documentOutput : loadedDocs) {
            if (documentOutput.getName().equals(document.getName())) {
                index = loadedDocs.indexOf(documentOutput);
                return "documentOutput?faces-redirect=true&i=2&doc=" + document.getName();
            }
        }
        mainDocument = documentRepository.findById(document.getId());
        Hibernate.initialize(mainDocument.getParagraphs());
        for (Paragraph paragraph : mainDocument.getParagraphs()) {
            Hibernate.initialize(paragraph.getSentences());
            for (Sentence sentence : paragraph.getSentences()) {
                Hibernate.initialize(sentence.getAnnotationSets());
                for (AnnotationSet annotationSet : sentence.getAnnotationSets()) {
                    Hibernate.initialize(annotationSet.getLayers());
                    for (Layer layer : annotationSet.getLayers()){
                        Hibernate.initialize(layer.getLabels());
                    }
                }
            }
        }
        loadedDocs.add(new DocumentOutput(mainDocument));
        index = loadedDocs.size() -1;
        return "documentOutput?faces-redirect=true&i=2&doc=" + document.getName();
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

    public void onTabDocClose(TabCloseEvent event) {
        String id = event.getTab().getTitle(); {
            for (DocumentOutput output : loadedDocs) {
                if (output.getName().equals(id)) {
                    loadedDocs.remove(output);
                    break;
                }
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
        return "documentOutput?faces-redirect=true&i=2";
    }

    private void clear() {
        loadedFrames = new ArrayList<FrameOutput>();
        loadedLUs = new ArrayList<LUOutput>();
        loadedDocs = new ArrayList<DocumentOutput>();
        mainFrame = null;
        mainLU = null;
        mainDocument = null;
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

    public void orderLU (String filter) {
        List<LightLU> allLU= new ArrayList<LightLU>();
        for (LexUnit lu : lexUnitRepository.findAll()) {
            if (matchSearch(filter, lu.getName())) {
                allLU.add(new LightLU(lu.getId(), lu.getName(), lu.getFrame().getName()));
            } else if (matchSearch(filter, lu.getFrame().getName())) {
                allLU.add(new LightLU(lu.getId(), lu.getName(), lu.getFrame().getName()));
            }
        }
        orderedLU = allLU;
    }

    public List<LightLU> getOrderedLU (String filter) {
        if (orderedLU.isEmpty() ) {
            List <LightLU> allLu = new ArrayList<LightLU>();
            for (LexUnit lu : lexUnitRepository.findAll()) {
                allLu.add(new LightLU(lu.getId(), lu.getName(), lu.getFrame().getName()));
            }
            lightLUs = allLu;
            orderedLU = allLu;
        } else if (filter.isEmpty()) {
            orderedLU = lightLUs;
        }
        return orderedLU;
    }

    private boolean matchSearch (String query, String name) {
        return ((name.equalsIgnoreCase(query))
                ||(name.toLowerCase().contains(query.toLowerCase()))
                ||(query.toLowerCase().contains(name.toLowerCase())));
    }

    public List<DocumentOutput> getLoadedDocs() {
        return loadedDocs;
    }
}
