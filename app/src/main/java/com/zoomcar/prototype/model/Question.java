package com.zoomcar.prototype.model;

import java.util.Map;

/**
 * Represents a Question.
 * A question points to a specific coordinate in the image and has a corresponding answer group.
 * Has a default answer in the answer category/type for pre-selection purposes.
 */
public class Question {
    public int id;
    public String text;
    public float[] coordinates;
    public int answerGroupId;
    public int defaultAnswerId;

    public Question(int id, String text, float[] coordinates, int answerGroupId, int defaultAnswerId) {
        this.id = id;
        this.text = text;
        this.coordinates = coordinates;
        this.answerGroupId = answerGroupId;
        this.defaultAnswerId = defaultAnswerId;
    }

    public Question(int id, String text, float[] coordinates, int answerGroupId) {
        this.id = id;
        this.text = text;
        this.coordinates = coordinates;
        this.answerGroupId = answerGroupId;
        this.defaultAnswerId = -1;
    }
}
