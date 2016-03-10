package jp.keio.jfn.wat.KWIC;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.domain.FrameElement;
import jp.keio.jfn.wat.domain.LexUnit;
import org.hibernate.Hibernate;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ApplicationScoped
public class FrameAssociatesService {
    private Frame mainFrame;
    private TreeNode root;

    public TreeNode createTree(Frame frame){
        System.out.println(frame.getName()); //TODO why does it get called so often??
        mainFrame = frame;
        root = new DefaultTreeNode("frame", null);
        addFEs();
        addLUs();
        return root;
    }

    private void addFEs() {
        for (FrameElement fe : mainFrame.getFrameElements()) {
            TreeNode feTN = new DefaultTreeNode(fe.getName(), root);
           // fet.setType(fe.getCore());
        }
    }

    private void addLUs() {
        List<LexUnit> lus = mainFrame.getLexUnits();
        if(lus.isEmpty()){
            TreeNode LUs = new DefaultTreeNode("- No LU's", root);
        } else {
            TreeNode LUs = new DefaultTreeNode("LU's", root);
            for (LexUnit lu : mainFrame.getLexUnits()) {
                TreeNode luTN = new DefaultTreeNode(lu.getName(), LUs);
            }
        }
    }
}