package jp.keio.jfn.wat.KWIC;

import javax.faces.bean.ManagedBean;

/**
 * Created by Quiri on 15-3-2016.
 */
@ManagedBean
public class KwicView {

    public String getIcon(String type){
        if(type.matches("Core")){
            return "ui-icon-bullet";
        }else if(type.matches("Peripheral")){
            return "ui-icon-radio-off";
        }else if(type.matches("Extra-Thematic")){
            return "ui-icon-arrow-4";
        }else if(type.matches("Core-Unexpressed")){
            return "ui-icon-cancel";
        }else{
            return "ui-icon-help";
        }
    }
}
