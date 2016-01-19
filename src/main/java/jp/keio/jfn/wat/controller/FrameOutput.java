package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jfn on 1/18/16.
 */
public class FrameOutput {

    private String name;

    private String definition;

    private List<FrameElement> coreFrameEl = new ArrayList<FrameElement>();

    private List<FrameElement> nonCoreFrameEl = new ArrayList<FrameElement>();

    private List<LightLU> allLexUnits = new ArrayList<LightLU>();

    private List<String> coreSets = new ArrayList<String>();

    private List<Map.Entry<String, Frame>> relations = new ArrayList<Map.Entry<String, Frame>>();

    public FrameOutput (Frame mainFrame) {
        findFrameElements(mainFrame);
        for (LexUnit lu : mainFrame.getLexUnits()) {
            allLexUnits.add(new LightLU(lu.getId(), lu.getName(), mainFrame.getName()));
        }
        displayFrameRelations(mainFrame);
        name = mainFrame.getName();
        definition = mainFrame.getDefinition();
    }

    private void findFrameElements (Frame currentFrame) {
        for (FrameElement fe : currentFrame.getFrameElements()) {
            if (fe.getType().equals("Core")) {
                coreFrameEl.add(fe);
            } else {
                nonCoreFrameEl.add(fe);
            }
        }
    }

    private void displayFrameRelations (Frame currentFrame) {
        HashMap<String, List<Frame>> mapRelations = new HashMap<String, List<Frame>>();
        for (FrameRelation rel : currentFrame.getFrameRelations1()) {
            RelationType type = rel.getRelationType();
            if ((type.getId() != 4) &&(type.getId()!=5) && (type.getId() !=6)) {
                Frame subFrame = rel.getFrame2();
                if ((subFrame != null) && (subFrame.getId() != 100)){
                    insertInMap(mapRelations, processFrameRelation(type, true), subFrame);
                }
            } else if (type.getId() == 6) {
                for (FERelation feRelation : rel.getFerelations()) {
                    String result = "{" + feRelation.getFrameElement2().getName()+", "+ feRelation.getFrameElement1().getName()+"}";
                    coreSets.add(result);
                }
            }
        }
        for (FrameRelation rel : currentFrame.getFrameRelations2()) {
            RelationType type = rel.getRelationType();
            if ((type.getId() != 4) &&(type.getId()!=5) && (type.getId() !=6)) {
                Frame superFrame = rel.getFrame1();
                if ((superFrame != null) && (superFrame.getId() != 100)){
                    insertInMap(mapRelations, processFrameRelation(type, false), superFrame);
                }
            }
        }
        relations = new ArrayList(mapRelations.entrySet());
    }

    private void insertInMap(HashMap<String, List<Frame>> mapRelations, String entry, Frame frame) {
        if (mapRelations.containsKey(entry)) {
            mapRelations.get(entry).add(frame);
        } else {
            List<Frame> newList = new ArrayList<Frame>();
            newList.add(frame);
            mapRelations.put(entry,newList);
        }
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
                result=isFrame1?"":"";
                break;
            case 6:
                result=isFrame1?"Core Set":"Core Set";
                break;
            case 7:
                result=isFrame1?"Is Excluded by":"Includes";
                break;
            case 8:
                result=isFrame1?"Is Required by":"Requires";
                break;
            case 9:
                result=isFrame1?"Is Causative of":"Is Inchoative of";
                break;
            case 10:
                result=isFrame1?"Is Causative of":"Is Inchoative of";
                break;
            case 11:
                result=isFrame1?"Is Preceded by":"Precedes";
                break;
            case 12:
                result=isFrame1?"Is Perspectivized in":"Perspective on";
                break;
            default:
                break;
        }
        return result;
    }


    public List<FrameElement> getCoreFrameEl() {
        return coreFrameEl;
    }

    public List<FrameElement> getNonCoreFrameEl () {
        return nonCoreFrameEl;
    }

    public List<LightLU> getAllLexUnits () {
        return allLexUnits;
    }

    public String getName() {
        return name;
    }

    public List<Map.Entry<String, Frame>> getRelations() {
        return relations;
    }

    public String getDefinition() {
        return definition;
    }

    public List<String> getCoreSets() {
        if (coreSets.size() > 0) {
            if (!coreSets.get(0).equals("FE Core sets:")) {
                coreSets.add(0,"FE Core sets:");
            }
        }
        return coreSets;
    }
}
