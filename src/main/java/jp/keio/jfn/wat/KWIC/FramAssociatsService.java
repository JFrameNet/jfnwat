package jp.keio.jfn.wat.KWIC;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.domain.FrameElement;
import jp.keio.jfn.wat.domain.LexUnit;
import jp.keio.jfn.wat.repository.FrameRepository;
import org.hibernate.Hibernate;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;


@ManagedBean(name = "framAssosiatsService")
@ApplicationScoped
public class FramAssociatsService {

    @Autowired
    FrameRepository frameRepository;

    public TreeNode createFramAssociats(String frame) {
        TreeNode root = new DefaultTreeNode(frame, null);

        Frame mainFrame = frameRepository.findByName(frame).get(0);
        Hibernate.initialize(mainFrame.getFrameElements());
        Hibernate.initialize(mainFrame.getLexUnits());

        for (FrameElement fe : mainFrame.getFrameElements()) {
            TreeNode fet = new DefaultTreeNode(fe.getName(), root);
            fet.setType(fe.getCore());
        }

        TreeNode LUs = new DefaultTreeNode("LU's", root);

        for (LexUnit lu : mainFrame.getLexUnits()) {
            new DefaultTreeNode(lu.getName(), LUs);
        }

        return root;
    }
}