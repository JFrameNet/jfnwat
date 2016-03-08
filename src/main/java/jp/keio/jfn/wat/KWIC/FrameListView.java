package jp.keio.jfn.wat.KWIC;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

import jp.keio.jfn.wat.domain.Frame;
import org.primefaces.model.TreeNode;

@ViewScoped
public class FrameListView implements Serializable {

    private TreeNode root;

   /* private X selectedElement; */

    public void init(String frame) {
        root = new FramAssociatsService().createFramAssociats(frame);
    }

    public TreeNode getRoot() {
        return root;
    }

    /*

    public Document getSelectedDocument() {
        return selectedDocument;
    }

    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }
    */
}
