package com.zoomcar.prototype.model;

import java.util.ArrayList;
import java.util.Map;

/**
 * Represents a Section of car
 */
public class Section {
    public int id;
    public String text;
    public int[] questionIds;
    public int drawableId;

    public Section(int id, String text, int[] questionIds) {
        this.id = id;
        this.text = text;
        this.questionIds = questionIds;
    }

    public Section(int id, String text, int[] questionIds, int drawableId) {
        this.id = id;
        this.text = text;
        this.questionIds = questionIds;
        this.drawableId = drawableId;
    }
}
