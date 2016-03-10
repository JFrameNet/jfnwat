package jp.keio.jfn.wat.webreport;

import jp.keio.jfn.wat.domain.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a lexical unit object, with fewer variables (only id, name and frame).
 * It is used for the Web Report, when all other properties of a lexical unit are not needed.
 */
public class LightLU {
    private int id;
    private String name;
    private String frame;
    private List<Status> statuses = new ArrayList<Status>();

    /**
     * Initialization
     */
    public LightLU (int id, String name, String frame) {
        this.id = id;
        this.name = name;
        this.frame = frame;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFrame() {
        return frame;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
}
