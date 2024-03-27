package com.example.application_ai_la_trieu_phu;

import java.util.List;

public class Questions {
    private int numberkey;
    private String Question;

    public List<Answers> listanswers;

    public Questions(int numberkey, String question, List<Answers> listanswers) {
        this.numberkey = numberkey;
        Question = question;
        this.listanswers = listanswers;
    }


    public int getNumberkey() {
        return numberkey;
    }

    public void setNumberkey(int numberkey) {
        this.numberkey = numberkey;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public List<Answers> getListanswers() {
        return listanswers;
    }

    public void setListanswers(List<Answers> listanswers) {
        this.listanswers = listanswers;
    }
}
