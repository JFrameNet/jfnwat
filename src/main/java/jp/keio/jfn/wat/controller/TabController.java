package jp.keio.jfn.wat.controller;

import java.util.*;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.Utils;
import jp.keio.jfn.wat.webreport.domain.*;
import jp.keio.jfn.wat.webreport.repository.*;
import jp.keio.jfn.wat.webreport.DocumentOutput;
import jp.keio.jfn.wat.webreport.FrameOutput;
import jp.keio.jfn.wat.webreport.LUOutput;
import jp.keio.jfn.wat.webreport.LightLU;
import org.hibernate.Hibernate;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@ManagedBean
@RestController
@Component
@Scope("session")
/**
 * This class controls the tabulations in the Web Report.
 * It keeps all the loaded frames, lexical units and documents.
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

    private List<LightLU> lightLUs = new ArrayList<LightLU>();

    @Autowired
    FrameRepository frameRepository;

    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    DocumentRepository documentRepository;

    /**
     * Adds a new FrameOutput object to the loaded frames list.
     *
     * @param name is the name of the frame to be added
     */
    @Transactional
    public void addFrame(String name) {
        for (FrameOutput frameOutput : loadedFrames) {
            if (frameOutput.getName().equals(name)) {
                index = loadedFrames.indexOf(frameOutput);
                return;
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
    }

    /**
     * Adds a new FrameOutput object to the loaded frames list and returns the address of the Frame Index for
     * redirection.
     *
     * @param name is the name of the frame to be added
     */
    @Transactional
    public String addFrameRedirect (String name) {
        addFrame(name);
        return "frameOutput?faces-redirect=true&i-0";
    }

    /**
     * Adds a new LUOutput object to the loaded lexical units list.
     *
     * @param id is the id of the lexical unit to be added
     */
    @Transactional
    public void addLU(int id) {
        // TODO: 2/3/16 change to check id instead of name (many LUs can have the same name)
        for (LUOutput luOutput : loadedLUs) {
            if (luOutput.getLightLU().getId() == id) {
                index = loadedLUs.indexOf(luOutput);
                return;
            }
        }
        mainLU = lexUnitRepository.findById(id);
        Hibernate.initialize(mainLU.getAnnotationSets());
        for (AnnotationSet annotationSet : mainLU.getAnnotationSets()) {
            Hibernate.initialize(annotationSet.getLayers());
            for (Layer layer : annotationSet.getLayers()){
                Hibernate.initialize(layer.getLabels());
            }
        }
        loadedLUs.add(new LUOutput(mainLU));
        index = loadedLUs.size() -1;
    }

    /**
     * Adds a new LUOutput object to the loaded lexical units list and returns the address of the Lexical Unit Index for
     * redirection.
     *
     * @param id is the id of the lexical unit to be added
     */
    @Transactional
    public String addLURedirect(int id) {
        addLU(id);
        return "lexUnitOutput?faces-redirect=true&i=1";
    }

    /**
     * Adds a new DocumentOutput object to the loaded documents list.
     *
     * @param document is the id of the document to be added
     */
    @Transactional
    public void addDoc(Document document) {
        for (DocumentOutput documentOutput : loadedDocs) {
            if (documentOutput.getName().equals(document.getName())) {
                index = loadedDocs.indexOf(documentOutput);
                return;
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
    }

    /**
     * Adds a new DocumentOutput object to the loaded documents list and returns the address of the Document Index for
     * redirection.
     *
     * @param document is the id of the document to be added
     */
    @Transactional
    public String addDocRedirect(Document document) {
        addDoc(document);
        return "documentOutput?faces-redirect=true&i=2";
    }

    /**
     * Removes a frame from the loaded frames list when a user closes the corresponding tab.
     */
    public void onTabClose(TabCloseEvent event) {
        String name = event.getTab().getTitle();
        for (FrameOutput frame : loadedFrames) {
            if (frame.getName().equals(name)) {
                loadedFrames.remove(frame);
                break;
            }
        }
    }

    public void onTabChange(TabChangeEvent event) {
    }

    /**
     * Removes a lexical unit from the loaded lexical unit list when a user closes the corresponding tab.
     */
    // TODO: 2/3/16 change to check id instead of name (many LUs can have the same name)
    public void onTabLUClose(TabCloseEvent event) {
        String id = event.getTab().getTitle();
        for (LUOutput ouput : loadedLUs) {
            if (ouput.getLightLU().getName().equals(id)) {
                loadedLUs.remove(ouput);
                break;
            }
        }
    }

    /**
     * Removes a document from the loaded documents list when a user closes the corresponding tab.
     */
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

    /**
     * Called when the users goes back to the home page. It resets all the loaded elements (frames, LUs, documents).
     */
    private void clear() {
        loadedFrames = new ArrayList<FrameOutput>();
        loadedLUs = new ArrayList<LUOutput>();
        loadedDocs = new ArrayList<DocumentOutput>();
        mainFrame = null;
        mainLU = null;
        mainDocument = null;
    }

    /**
     * Filter lexical units when a user types a search string in the input field of the lexical unit index.
     * Updates this.orderedLU with all the lexical units whose name or frame name matches the search string.
     */
    public void orderLU (String filter) {
        List<LightLU> allLU= new ArrayList<LightLU>();
        for (LexUnit lu : lexUnitRepository.findAll()) {
            if (Utils.matchSearch(filter, lu.getName())) {
                allLU.add(new LightLU(lu.getId(), lu.getName(), lu.getFrame().getName()));
            } else if (Utils.matchSearch(filter, lu.getFrame().getName())) {
                allLU.add(new LightLU(lu.getId(), lu.getName(), lu.getFrame().getName()));
            }
        }
        orderedLU = allLU;
    }

    /**
     * Getter for this.orderedLU. If the list if empty and the string search is empty it returns all lexical units.
     */
    public List<LightLU> getOrderedLU (String filter) {
        if (orderedLU.isEmpty() && filter.isEmpty()) {
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

    public List<DocumentOutput> getLoadedDocs() {
        return loadedDocs;
    }

    public List<FrameOutput> getLoadedFrames() {
        return loadedFrames;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<LUOutput> getLoadedLUs() {
        return loadedLUs;
    }

    public int getIndex() {
        return index;
    }

    public void setLexUnitRepository(LexUnitRepository l) {
        lexUnitRepository = l;
    }
}
