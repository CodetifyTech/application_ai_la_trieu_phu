package com.example.application_ai_la_trieu_phu;

import java.util.List;

public class function_Questions {
    private int numberkey;
    private String Question;

    public List<function_Answers> listanswers;

    public function_Questions(int numberkey, String question, List<function_Answers> listanswers) {
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

    public List<function_Answers> getListanswers() {
        return listanswers;
    }

    public void setListanswers(List<function_Answers> listanswers) {
        this.listanswers = listanswers;
    }
}
