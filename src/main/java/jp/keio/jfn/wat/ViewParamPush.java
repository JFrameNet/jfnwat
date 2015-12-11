package jp.keio.jfn.wat;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

@ManagedBean
@RequestScoped
public class ViewParamPush implements Serializable{

    private String data;

    private String keyword;

    public String getData() {
        return data;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void prerender() {
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish("/viewparam", data);
    }
}