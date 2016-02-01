package jp.keio.jfn.wat.controller;

import java.util.*;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    private List<String> orderedFrames = new ArrayList<String>();

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
            orderedFrames = sortedNames;
            return sortedNames;
        } else {
            return orderedFrames;
        }
    }

    public String processDef(FrameOutput frameOutput, String def) {
        Pattern pattern = Pattern.compile("<fen>(.+?)</fen>");
        Matcher matcher = pattern.matcher(def);
        while (matcher.find()) {
            String word = matcher.group(1);
            String color = "";
            int index = frameOutput.getAllFENames().indexOf(word);
            if (index != -1) {
                color = TabController.allColors.get(index);
            }
            def = def.replaceAll("<fen>"+word+"</fen>", "<font color="+color+">" + word + "</font>");
        }
        for (String fe : frameOutput.getAllFENames()) {
            def = otherProcess(frameOutput, def, fe);
        }
        return  def;
    }

    private String otherProcess(FrameOutput frameOutput, String def, String fe) {
        Pattern pattern = Pattern.compile("<fex name="+'"'+fe+'"'+">(.+?)</fex>");
        Matcher matcher = pattern.matcher(def);
        while (matcher.find()) {
            String word = matcher.group(1);
            String color = TabController.allColors.get(frameOutput.getAllFENames().indexOf(fe));
            def = def.replaceAll("<fex name="+'"'+fe+'"'+">"+word+"</fex>", "<font color="+color+">" + word + "</font>");
        }
        return def;
    }
}