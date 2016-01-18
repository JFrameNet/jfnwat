package jp.keio.jfn.wat.controller;

import jp.keio.jfn.wat.domain.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfn on 1/18/16.
 */
public class LightLU {
    private int id;
    private String name;
    private String frame;
    private List<Status> statuses = new ArrayList<Status>();

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
