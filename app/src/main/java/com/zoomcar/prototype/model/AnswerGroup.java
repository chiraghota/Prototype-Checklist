package com.zoomcar.prototype.model;

import java.util.ArrayList;

/**
 * Contains multiple answers in an array.
 */
public class AnswerGroup {
    public int id;
    public String text;
    public int[] answerIds;

    public AnswerGroup(int id, String text, int[] answerIds) {
        this.id = id;
        this.text = text;
        this.answerIds = answerIds;
    }
}
