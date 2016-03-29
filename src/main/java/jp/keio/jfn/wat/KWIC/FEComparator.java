package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.webreport.domain.FrameElement;

import java.util.Comparator;

/**
 * Created by jfn on 3/16/16.
 */
public class FEComparator implements Comparator<FrameElement> {
    public enum FeTypes {
        CORE, PERIPHERAL, EXTRA, UNEX
    }


    @Override
    public int compare(FrameElement x, FrameElement y){
        return typeToEnum(x.getType()).compareTo(typeToEnum(y.getType()));
    }


    public FeTypes typeToEnum(String type){
        if(type.matches("Core")){
            return FeTypes.CORE;
        }else if(type.matches("Peripheral")){
            return FeTypes.PERIPHERAL;
        }else if(type.matches("Extra-Thematic")){
            return FeTypes.EXTRA;
        }else if(type.matches("Core-Unexpressed")){
            return FeTypes.UNEX;
        }
        return null;
    }
}
