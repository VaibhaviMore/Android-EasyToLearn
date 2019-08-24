package com.example.sam.easytolearn_finalproject.model;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String questionId;
    private String quesString;
    private String noOfAnswers;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuesString() {
        return quesString;
    }

    public void setQuesString(String quesString) {
        this.quesString = quesString;
    }

    public String getNoOfAnswers() {
        return noOfAnswers;
    }

    public void setNoOfAnswers(String noOfAnswers) {
        this.noOfAnswers = noOfAnswers;
    }

    public Question(String questionId, String quesString, String noOfAnswers) {
        this.questionId=questionId;
        this.quesString = quesString;
        this.noOfAnswers = noOfAnswers;

    }

    public Question() {
    }




}
