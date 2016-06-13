package jp.keio.jfn.wat.KWIC.viewelements;

import java.util.Comparator;
import jp.keio.jfn.wat.domain.Frame;

/**
 * Created by jfn on 6/7/16.
 */
public class FrameNameComperator implements Comparator<Frame> {

    @Override
    public int compare(Frame x, Frame y){
        return x.getName().compareTo((y.getName()));
    }
}
