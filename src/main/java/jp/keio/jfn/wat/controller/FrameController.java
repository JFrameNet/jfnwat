package jp.keio.jfn.wat.controller;

import java.util.*;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import jp.keio.jfn.wat.domain.*;
import jp.keio.jfn.wat.repository.*;
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


    public void setFrameRepository(FrameRepository f) {
        frameRepository = f;
    }
}
