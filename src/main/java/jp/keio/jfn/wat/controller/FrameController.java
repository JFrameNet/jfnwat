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

    public List<ElementTag> processDef (FrameOutput frameOutput, String def) {
        return auxFun(def, "<fen>", "</fen", frameOutput);
    }

    private List<ElementTag> auxFun (String def, String a, String c, FrameOutput frameOutput){
        String b = a;
        List<ElementTag> result =new ArrayList<ElementTag>();
        int imax = def.length();
        int i = 0;
        while (i < imax) {
            int index = def.substring(i,imax).indexOf(b);
            if (index != -1) {
                String word = def.substring(i,i+index);
                ElementTag el = new ElementTag(word, "");
                i += index + b.length();
                if (b.equals(a)) {
                    b = c;
                } else {
                    el.setWordColor(TabController.allColors.get(frameOutput.getAllFENames().indexOf(word)));
                    b = a;
                }
                result.add(el);
            } else {
                break;
            }
        }
        result.add(new ElementTag(def.substring(i,imax), ""));
        return result;
    }
}