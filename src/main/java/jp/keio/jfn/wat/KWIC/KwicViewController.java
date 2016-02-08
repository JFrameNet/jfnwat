package jp.keio.jfn.wat.KWIC;

import javax.faces.bean.ManagedBean;

/**
 * Created by Quiri on 3-2-2016.
 */

@ManagedBean
public class KwicViewController {

    public String toKwic() {
        return "kwicPage?faces-redirect=true";
    }
}
