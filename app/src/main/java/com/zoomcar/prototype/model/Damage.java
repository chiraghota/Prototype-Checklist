package com.zoomcar.prototype.model;

public class Damage {
    public int sectionId;
    public int questionId;
    public int answerGroupId;
    public int answerId;
    public boolean isNew;

    public Damage(int sectionId, int questionId, int answerGroupId, int answerId) {
        this.sectionId = sectionId;
        this.questionId = questionId;
        this.answerGroupId = answerGroupId;
        this.answerId = answerId;
        this.isNew = true;
    }
}
