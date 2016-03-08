package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.repository.FrameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Quiri on 8-3-2016.
 */
@ManagedBean
@RestController
@Scope("view")
public class FrameIndexController implements Serializable {
    @Autowired
    FrameRepository frameRepository;

    protected String filter = "";

    protected List<String> orderedFrames = new ArrayList<String>();

    /**
     * Filter frames when a user types a search string in the input field of the frame index.
     * Updates this.orderedFrames with all the frames whose name matches the search string.
     * Sorts the frames by name.
     */
    public void orderFrames() {
        List <String> sortedNames = new ArrayList<String>();
        for (Frame frame : frameRepository.findAll()) {
            if (Utils.matchSearch(filter, frame.getName())) {
                sortedNames.add(frame.getName());
            }
        }
        Collections.sort(sortedNames);
        orderedFrames = sortedNames;
    }


    /**
     * Getter for orderedFrames. If the list if empty and the string search is empty it returns all frames.
     */
    public List<String> getOrderedFrames () {
        if (orderedFrames.isEmpty() && filter.isEmpty()) {
            List <String> sortedNames = new ArrayList<String>();
            for (Frame frame : frameRepository.findAll()) {
                sortedNames.add(frame.getName());
            }
            Collections.sort(sortedNames);
            orderedFrames = sortedNames;
            return sortedNames;
        } else {
            return orderedFrames;
        }
    }

    public void setOrderedFrames (List<String> list) {
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
