package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.controller.FrameIndexController;
import org.primefaces.model.TreeNode;
import org.springframework.web.bind.annotation.RestController;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Quiri on 8-3-2016.
 */
@ManagedBean(name = "kwicIndex")
@RestController
public class KwicFrameIndexController extends FrameIndexController {


/*
    private TreeNode getFrameTree(String frame){
        FrameListView view = new FrameListView();
        view.init(frame);
        return view.getRoot();
    }
*/
}
