package jp.keio.jfn.wat.controller;

import java.util.*;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.Utils;
import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.*;
import jp.keio.jfn.wat.webreport.FrameOutput;
import jp.keio.jfn.wat.webreport.LightLU;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is a controller for the frame index. It handles operations on FrameOutput objects.
 */
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

    private List<String> orderedFrames = new ArrayList<String>();

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
     * Returns a list of all the lexical units belonging to the frame associated with the FrameOutput object.
     */
    @Transactional
    public List<LightLU> lexUnitsByFrame (FrameOutput frameOutput) {
        List<LightLU> list = new ArrayList<LightLU>();
        Frame mainFrame = frameRepository.findByName(frameOutput.getName()).get(0);
        for (LexUnit lu : mainFrame.getLexUnits()) {
            Hibernate.initialize(lu.getStatuses());
            LightLU lightLU = new LightLU(lu.getId(), lu.getName(), mainFrame.getName());
            lightLU.setStatuses(lu.getStatuses());
            list.add(lightLU);
        }
        return list;
    }

    /**
     * Adds html tags to change font color of some words in a String.
     * In the JFN database, definitions of Frames and Frame Elements have tags to indicate that the word is a frame
     * element. These tags are not consistent throughout the database.
     *
     * @param def the String to be processed.
     * @return a String with html tags
     */
    public String processDef(FrameOutput frameOutput, String def) {
        // Looks for tags with the format <fen>
        Pattern pattern = Pattern.compile("<fen>(.+?)</fen>");
        Matcher matcher = pattern.matcher(def);
        while (matcher.find()) {
            String word = matcher.group(1);
            String color = "";
            int index = frameOutput.getAllFENames().indexOf(word);
            if (index != -1) {
                color = Utils.allColors.get(index);
            }
            def = def.replaceAll("<fen>"+word+"</fen>", "<font color="+color+">" + word + "</font>");
        }
        // Looks for other possible tags based on frame elements name
        for (String fe : frameOutput.getAllFENames()) {
            def = otherProcess(frameOutput, def, fe);
        }
        return  def;
    }
    /**
     * These method is used to find tags with the format <fex name="FrameElementName"></fex>
     */
    private String otherProcess(FrameOutput frameOutput, String def, String fe) {
        Pattern pattern = Pattern.compile("<fex name="+'"'+fe+'"'+">(.+?)</fex>");
        Matcher matcher = pattern.matcher(def);
        while (matcher.find()) {
            String word = matcher.group(1);
            String color = Utils.allColors.get(frameOutput.getAllFENames().indexOf(fe));
            def = def.replaceAll("<fex name="+'"'+fe+'"'+">"+word+"</fex>", "<font color="+color+">" + word + "</font>");
        }
        return def;
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