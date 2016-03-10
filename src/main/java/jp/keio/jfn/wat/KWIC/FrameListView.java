package jp.keio.jfn.wat.KWIC;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import jp.keio.jfn.wat.domain.Frame;
import jp.keio.jfn.wat.repository.FrameRepository;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;

@ViewScoped
public class FrameListView implements Serializable {
    private final Frame frame;

    @ManagedProperty("#{frameAssociatesService}")
    private FrameAssociatesService service;

    private TreeNode root;
    private String selectedElement;

    public FrameListView(Frame frame) {
        this.frame = frame;
        this.service = new FrameAssociatesService();
    }

    @PostConstruct
    public void build(){
        root = service.createTree(frame);
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setService(FrameAssociatesService service) {
        this.service = service;
    }

    public String getSelectedElement() {
        return selectedElement;
    }

    public void setSelectedElement(String selectedElement) {
        this.selectedElement = selectedElement;
    }
}
