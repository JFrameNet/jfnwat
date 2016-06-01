package jp.keio.jfn.wat.KWIC.controller;

import jp.keio.jfn.wat.KWIC.viewelements.FEComparator;
import jp.keio.jfn.wat.KWIC.viewelements.FrameListView;
import jp.keio.jfn.wat.webreport.controller.FrameIndexController;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.domain.FrameElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Quiri on 8-3-2016.
 */
@ManagedBean
@Controller
@Scope("view")
public class KwicIndexController extends FrameIndexController {


    @Transactional(readOnly = true)
    public TreeNode getFrameTree(String name){
        Frame frame = frameRepository.findByName(name).get(0);
        System.out.println("createFrameTree");
        System.out.println(name);
        FrameListView view = new FrameListView(frame);
        view.build();
        return view.getRoot();
    }

    @Transactional(readOnly = true)
    public List<FrameElement> getFrameElements(String name){
        Frame frame = frameRepository.findByName(name).get(0);
        List<FrameElement> feList = frame.getFrameElements();
        return getOrderedFEs(feList);
    }

    private List<FrameElement> getOrderedFEs(List<FrameElement> feList) {
        feList.sort(new FEComparator());
        return feList;
    }

    /*
    @Override
    public void orderFrames() { //TODO replace find all by appropriate jpa
        List <String> sortedNames = new ArrayList<String>();
        for (Frame frame : frameRepository.findByNameLike("%"+filter+"%")) {
                if (!frame.getName().isEmpty()) {
                    sortedNames.add(frame.getName());
                }
        }
        Collections.sort(sortedNames);
        orderedFrames = sortedNames;
    }
    */
}
