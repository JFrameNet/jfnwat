package jp.keio.jfn.wat.KWIC;

import javax.faces.bean.ManagedBean;

/**
 * Created by Quiri on 15-3-2016.
 */
@ManagedBean
public class KwicView {

    public String getIcon(String type){
        if(type.matches("Core")){
            return "fa fa-circle";  //"ui-icon-bullet";
        }else if(type.matches("Peripheral")){
            return "fa fa-circle-o";//"ui-icon-radio-off";
        }else if(type.matches("Extra-Thematic")){
            return "fa fa-arrows";//"ui-icon-arrow-4";
        }else if(type.matches("Core-Unexpressed")){
            return "fa fa-adjust";//"ui-icon-cancel";
        }else{
            return "fa fa-question";//"ui-icon-help";
        }
    }
}
