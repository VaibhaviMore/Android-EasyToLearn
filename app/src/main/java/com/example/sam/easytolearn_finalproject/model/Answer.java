package com.example.sam.easytolearn_finalproject.model;

public class Answer {

    private String answerId;
    private String answerString;
    private String questionId;
public Answer(){}

    public Answer(String answerId, String answerString, String questionId) {
        this.answerId = answerId;
        this.answerString = answerString;
        this.questionId = questionId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }



}
