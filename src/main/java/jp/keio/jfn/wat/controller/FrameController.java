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

    private String filter = "";

    private Frame currentFrame;

    private List<String> orderedFrames = new ArrayList<String>();

    private List<FrameElement> coreFrameEl;

    private List<FrameElement> nonCoreFrameEl;

    private List<FrameRelation> relations;

    private List<LexUnit> allLexUnits;

    public Frame frameDisplay (Frame mainFrame) {
        currentFrame = mainFrame;
        findFrameElements();
        findFrameRelations();
        findLexicalUnits();
        return currentFrame;
    }

    public Iterable<Frame> getAll () {
        return frameRepository.findAll();
    }

    public void orderFrames() {
        List <String> sortedNames = new ArrayList<String>();
        for (Frame frame : frameRepository.findAll()) {
            if (matchSearch(filter, frame.getName())) {
                sortedNames.add(frame.getName());
            }
        }
        Collections.sort(sortedNames);
        orderedFrames = sortedNames;
    }

    private void findFrameElements () {
        Iterable<FrameElement> allFE = frameElementRepository.findAll();
        List<FrameElement> frameCore = new ArrayList<FrameElement>();
        List<FrameElement> frameNonCore = new ArrayList<FrameElement>();
        for (FrameElement fe : allFE) {
            if (fe.getFrame().getId().equals(currentFrame.getId())) {
                if (fe.getType().equals("Core")) {
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
                mapRelations.put(processFrameRelation(relationType, true),rel.getFrame2());
            } else {
                mapRelations.put(processFrameRelation(relationType, false),rel.getFrame1());
            }
        }
    return new ArrayList(mapRelations.entrySet());
    }

    private String processFrameRelation (RelationType rel, boolean isFrame1) {
        String result="";
        switch (rel.getId()){
            case 1:
                result=isFrame1?"Is Inherited by":"Inherits from";
                break;
            case 2:
                result=isFrame1?"Has Subframe(s)":"Subframe of";
                break;
            case 3:
                result=isFrame1?"Is Used by":"Uses";
                break;
            case 4:
                result="See also";
                break;
            case 5:
                result=isFrame1?"Is Preceded by":"Precedes";
                break;
            case 6:
                result=isFrame1?"Is Causative of":"Is Inchoative of";
                break;
            case 7:
                result=isFrame1?"Is Perspectivized in":"Perspective on";
                break;
            default:
                break;
        }
        return result;
    }

    public void setCurrentFrame (Frame frame) {
        currentFrame = frame;
    }

    public Frame getCurrentFrame() {
        return currentFrame;
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

    public void setFilter (String f) {
        filter = f;
    }

    public String getFilter () {
        return filter;
    }

    private boolean matchSearch (String query, String name) {
        return ((name.equalsIgnoreCase(query))
                ||(name.toLowerCase().contains(query.toLowerCase()))
                ||(query.toLowerCase().contains(name.toLowerCase())));
    }

    public void setOrderedFrames (List<String> list) {
        orderedFrames = list;
    }

    public List<String> getOrderedFrames () {
        if (orderedFrames.isEmpty() && filter.isEmpty()) {
            List <String> sortedNames = new ArrayList<String>();
            for (Frame frame : frameRepository.findAll()) {
                sortedNames.add(frame.getName());
            }
            Collections.sort(sortedNames);
            return sortedNames;
        } else {
            return orderedFrames;
        }
    }
}