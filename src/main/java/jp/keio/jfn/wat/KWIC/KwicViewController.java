package jp.keio.jfn.wat.KWIC;

import org.springframework.web.bind.annotation.RestController;

import javax.faces.bean.ManagedBean;

/**
 * Created by Quiri on 3-2-2016.
 */

@ManagedBean
@RestController
public class KwicViewController {

    public String toKwic() { return "kwicPage?faces-redirect=true";}

    public void completeText(){
    }


}

