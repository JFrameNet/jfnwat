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
        HashMap<String, Frame> mapRelations = new HashMap<String, Frame>();
        for (FrameRelation rel : currentFrame.getFrameRelations1()) {
            mapRelations.put(processFrameRelation(rel.getRelationType(), true),rel.getFrame2());
        }
        for (FrameRelation rel : currentFrame.getFrameRelations2()) {
            mapRelations.put(processFrameRelation(rel.getRelationType(), false),rel.getFrame1());
        }
        relations = new ArrayList(mapRelations.entrySet());
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
}
