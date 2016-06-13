package jp.keio.jfn.wat.KWIC.controller;

import jp.keio.jfn.wat.KWIC.viewelements.FEComparator;
import jp.keio.jfn.wat.KWIC.viewelements.FrameNameComperator;
import jp.keio.jfn.wat.KWIC.viewelements.FrameListView;
import jp.keio.jfn.wat.Utils;
import jp.keio.jfn.wat.repository.FrameRepository;
import jp.keio.jfn.wat.repository.LexUnitRepository;

import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.domain.FrameElement;
import jp.keio.jfn.wat.webreport.FrameOutput;
import jp.keio.jfn.wat.webreport.controller.FrameController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Quiri on 8-3-2016.
 */
@ManagedBean
@Controller
@Scope("view")
public class KwicIndexController{
    @Autowired
    FrameRepository frameRepository;

    @Autowired
    LexUnitRepository lexUnitRepository;

    @Autowired
    FrameController frameController;

    private String filter = "";

    private List<Frame> orderedFrames = new ArrayList<Frame>();

    /**
     * Filter frames when a user types a search string in the input field of the kwic frame index.
     * Updates this.orderedFrames with all the frames whose name matches the search string.
     * Sorts the frames by name.
     * SAME AS IN FRAME INDEX BUT FRAME IPV FRAME NAME
     */
    public void orderFrames() { //TODO replace find all by appropriate jpa
        orderedFrames = new ArrayList<Frame>();
        for (Frame frame : frameRepository.findAll()) {
            if (Utils.matchSearch(filter, frame.getName())) {
                if (!frame.getName().isEmpty()) {
                    orderedFrames.add(frame);
                }
            }
        }
        orderedFrames.sort(new FrameNameComperator());
    }

    /**
     * Getter for orderedFrames. If the list is empty and the string search is empty it returns all frames.
     * SAME AS IN FRAME INDEX BUT FRAME IPV FRAME NAME
     */
    public List<Frame> getOrderedFrames () {
        if (orderedFrames.isEmpty() && filter.isEmpty()) {
            orderedFrames = new ArrayList<Frame>();
            for (Frame frame : frameRepository.findAll()) {
                if (!frame.getName().isEmpty()) {
                    orderedFrames.add(frame);
                }
            }
            orderedFrames.sort(new FrameNameComperator());
        }
            return orderedFrames;
    }

    @Transactional
    public String getFrameDef(int frameID) {
        Frame frame = frameRepository.findById(frameID);
        return frameController.processDef(new FrameOutput(frame), frame.getDefinition());
    }

    @Transactional(readOnly = true)
    public TreeNode getFrameTree(int frameID){
        Frame frame = frameRepository.findById(frameID);
/**/        System.out.println("createFrameTree");
/**/        System.out.println(frame.getName());
        FrameListView view = new FrameListView(frame);
        view.build();
        return view.getRoot();
    }

    @Transactional(readOnly = true)
    public List<FrameElement> getFrameElements(int id){
        Frame frame = frameRepository.findById(id);
        List<FrameElement> feList = frame.getFrameElements();
        return getOrderedFEs(feList);
    }

    private List<FrameElement> getOrderedFEs(List<FrameElement> feList) {
        feList.sort(new FEComparator());
        return feList;
    }


    public void setOrderedFrames (List<Frame> list) {
        orderedFrames = list;
    }

    public void setFilter (String f) {
        filter = f;
    }

    public String getFilter () {
        return filter;
    }

    public void setFrameRepository(FrameRepository f) {
        frameRepository = f;
    }

}
