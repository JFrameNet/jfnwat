package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.controller.FrameIndexController;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.Frame;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
public class KwicFrameIndexController extends FrameIndexController {
    @Transactional
    public TreeNode getFrameTree(String name){
        Frame frame = frameRepository.findByName(name).get(0);
        Hibernate.initialize(frame.getFrameElements());
        Hibernate.initialize(frame.getLexUnits());
        FrameListView view = new FrameListView(frame);
        view.build();
        return view.getRoot();
    }

}
