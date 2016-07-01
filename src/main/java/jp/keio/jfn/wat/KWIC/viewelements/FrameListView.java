package jp.keio.jfn.wat.KWIC.viewelements;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import jp.keio.jfn.wat.KWIC.FrameTreeService;
import jp.keio.jfn.wat.domain.Frame;
import org.primefaces.model.TreeNode;

@ViewScoped
@ManagedBean
public class FrameListView implements Serializable {
    private final Frame frame;

    @ManagedProperty("#{frameTreeService}")
    private FrameTreeService service;

    private TreeNode root;
    private TreeNode selectedNode;

    public FrameListView(Frame frame) {
        this.frame = frame;
        this.service = new FrameTreeService();
    }

    @PostConstruct
    public void build(){
        root = service.createTree(frame);
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setService(FrameTreeService service) {
        this.service = service;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
}
