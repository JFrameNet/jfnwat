package jp.keio.jfn.wat.KWIC;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import jp.keio.jfn.wat.webreport.domain.Frame;
import org.primefaces.model.TreeNode;

@ViewScoped
public class FrameListView implements Serializable {
    private final Frame frame;

    @ManagedProperty("#{frameTreeService}")
    private FrameTreeService service;

    private TreeNode root;
    private String selectedElement;

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

    public String getSelectedElement() {
        return selectedElement;
    }

    public void setSelectedElement(String selectedElement) {
        this.selectedElement = selectedElement;
    }
}
