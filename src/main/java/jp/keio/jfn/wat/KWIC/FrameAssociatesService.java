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

@ApplicationScoped
public class FrameAssociatesService {
    private Frame mainFrame;
    private TreeNode root;

    public TreeNode createTree(Frame frame){
        System.out.println(frame.getName()); //TODO why does it get called so often??
        mainFrame = frame;
        root = new DefaultTreeNode("frame", null);
        TreeNode fe = new DefaultTreeNode(frame.getName(), root);
        TreeNode lu = new DefaultTreeNode("LU", fe);
        addFEs();
   //   addLUs();
        return root;
    }

    @Transactional
    private void addFEs() {
    //    Hibernate.initialize(mainFrame.getFrameElements());

        for (FrameElement fe : mainFrame.getFrameElements()) {
            TreeNode fet = new DefaultTreeNode(fe.getName(), root);
           // fet.setType(fe.getCore());
        }
    }

    @Transactional
    private void addLUs() {
        TreeNode LUs = new DefaultTreeNode("LU's", root);
    //    Hibernate.initialize(mainFrame.getLexUnits());
        for (LexUnit lu : mainFrame.getLexUnits()) {
            new DefaultTreeNode(lu.getName(), LUs);
        }
    }
}