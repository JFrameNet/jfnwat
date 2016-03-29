package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.controller.FrameIndexController;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.webreport.domain.Frame;
import jp.keio.jfn.wat.webreport.domain.FrameElement;
import org.hibernate.Hibernate;
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
public class KwicFrameIndexController extends FrameIndexController {

    @Transactional(readOnly = true)
    public TreeNode getFrameTree(String name){
        System.out.println("getFrameTree");
        Frame frame = frameRepository.findByName(name).get(0);
  //      Hibernate.initialize(frame.getLexUnits());
        FrameListView view = new FrameListView(frame);
        view.build();
        return view.getRoot();
    }

    @Transactional(readOnly = true)
    public List<FrameElement> getFrameElements(String name){
        Frame frame = frameRepository.findByName(name).get(0);
//        Hibernate.initialize(frame.getFrameElements());
        List<FrameElement> feList = frame.getFrameElements();
        return getOrderedFEs(feList);
        //TODO figure out if i can use a binding
    }

    private List<FrameElement> getOrderedFEs(List<FrameElement> feList) {
        feList.sort(new FEComparator());
        return feList;
    }
}
