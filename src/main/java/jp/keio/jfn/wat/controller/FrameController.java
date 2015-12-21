package jp.keio.jfn.wat.controller;

import java.util.*;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.web.bind.annotation.RestController;

@ManagedBean
@RestController
@Scope("view")
public class FrameController implements Serializable {
    @Autowired
    FrameRepository frameRepository;
    
    @Autowired
    FrameElementRepository frameElementRepository;

    @Autowired
    FrameRelationRepository frameRelationRepository;

    @Autowired
    RelationTypeRepository relationTypeRepository;

    @Autowired
    LexUnitRepository lexUnitRepository;

    private Frame currentFrame;

    private String frameName;

    private List<FrameElement> coreFrameEl;

    private List<FrameElement> nonCoreFrameEl;

    private List<FrameRelation> relations;

    private List<LexUnit> allLexUnits;

    public Frame frameDisplay () {
        currentFrame = frameRepository.findByName(frameName).get(0);
        findFrameElements();
        findFrameRelations();
        findLexicalUnits();
        return currentFrame;
    }

    public Iterable<Frame> getAll () {
        return frameRepository.findAll();
    }

    public List<String> frameNameOrdered () {
        List <String> sortedNames = new ArrayList<String>();
        for (Frame frame : frameRepository.findAll()) {
            sortedNames.add(frame.getName());
        }
        Collections.sort(sortedNames);
        return sortedNames;
    }

    private void findFrameElements () {
        Iterable<FrameElement> allFE = frameElementRepository.findAll();
        List<FrameElement> frameCore = new ArrayList<FrameElement>();
        List<FrameElement> frameNonCore = new ArrayList<FrameElement>();
        for (FrameElement fe : allFE) {
            if (fe.getFrame().getId().equals(currentFrame.getId())) {
                if (fe.getCore().equals("Y")) {
                    frameCore.add(fe);
                } else {
                    frameNonCore.add(fe);
                }
            }
        }
        coreFrameEl = frameCore;
        nonCoreFrameEl = frameNonCore;
    }

    private void findFrameRelations () {
        List<FrameRelation> allRelations = new ArrayList<FrameRelation>();
        for (FrameRelation frameRelation : frameRelationRepository.findAll()) {
            if (frameRelation.getFrame1().getId().equals(currentFrame.getId())) {
                allRelations.add(frameRelation);
            }
            if (frameRelation.getFrame2().getId().equals(currentFrame.getId())) {
                allRelations.add(frameRelation);
            }
        }
        relations = allRelations;
    }

    private void findLexicalUnits () {
        List <LexUnit> lexicalUnits = new ArrayList<LexUnit>();
        for (LexUnit lu : lexUnitRepository.findAll()) {
            if (lu.getFrame().getId().equals(currentFrame.getId())) {
                lexicalUnits.add(lu);
            }
        }
        allLexUnits = lexicalUnits;
    }

    public List<Map.Entry<String, Frame>> displayFrameRelations () {
        HashMap<String, Frame> mapRelations = new HashMap<String, Frame>();
        for (FrameRelation rel : relations) {
            RelationType relationType = rel.getRelationType();
            if (rel.getFrame1().getId().equals(currentFrame.getId())) {
                mapRelations.put(relationType.getName() + " by",rel.getFrame2());
            } else {
                mapRelations.put(relationType.getName() + " from",rel.getFrame1());
            }
        }
    return new ArrayList(mapRelations.entrySet());
    }

    public void setCurrentFrame (Frame frame) {
        currentFrame = frame;
    }

    public Frame getCurrentFrame() {
        return currentFrame;
    }

    public void setFrameName (String name) {
        frameName = name;
    }

    public String getFrameName () {
        return frameName;
    }

    public List<FrameElement> getCoreFrameEl() {
        return coreFrameEl;
    }

    public List<FrameElement> getNonCoreFrameEl () {
        return nonCoreFrameEl;
    }

    public List<FrameRelation> getRelations () {
        return  relations;
    }

    public List<LexUnit> getAllLexUnits () {
        return allLexUnits;
    }

}