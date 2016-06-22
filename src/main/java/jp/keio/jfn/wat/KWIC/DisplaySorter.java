package jp.keio.jfn.wat.KWIC;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.el.MethodExpression;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jfn on 6/22/16.
 */
public class DisplaySorter implements Comparator<DTOSentenceDisplay>{


    private final List<Comparator<DTOSentenceDisplay>> comparators;

    public DisplaySorter(List<Comparator<DTOSentenceDisplay>> comparators) {
        this.comparators = comparators;
    }

    public int compare(DTOSentenceDisplay o1, DTOSentenceDisplay o2) {
        int i = 0;
        for (Comparator<DTOSentenceDisplay> c :comparators) {
            i = c.compare(o1, o2);
            if (i != 0) break;
        }
        return i;
    }

    static final Comparator<DTOSentenceDisplay> BY_KEY_WORD =
            new Comparator<DTOSentenceDisplay>(){
                @Override
                public int compare(DTOSentenceDisplay o1, DTOSentenceDisplay o2) {
                    return o1.getKeyWord().compareTo(o2.getKeyWord());
                }
            };

    static final Comparator<DTOSentenceDisplay> BY_PRE_CONTEXT =
            new Comparator<DTOSentenceDisplay>(){
                @Override
                public int compare(DTOSentenceDisplay o1, DTOSentenceDisplay o2) {
                    return  o1.getReverseBeginning().compareTo(o2.getReverseBeginning());
                }
            };

    static final Comparator<DTOSentenceDisplay> BY_POST_CONTEXT =
            new Comparator<DTOSentenceDisplay>(){
                @Override
                public int compare(DTOSentenceDisplay o1, DTOSentenceDisplay o2) {
                    return o1.getEnd().compareTo(o2.getEnd());
                }
            };

    static final Comparator<DTOSentenceDisplay> UN_SORTED =
            new Comparator<DTOSentenceDisplay>(){
                @Override
                public int compare(DTOSentenceDisplay o1, DTOSentenceDisplay o2) {
                    return 0;
                }
            };

    static final Comparator<SortMeta> SORT_PRESEDENSE =
            new Comparator<SortMeta>() {
                @Override
                public int compare(SortMeta o1, SortMeta o2) {
                    return o1.getSortField().compareTo(o2.getSortField());
                }
            };

}
