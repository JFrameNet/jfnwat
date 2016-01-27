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
}