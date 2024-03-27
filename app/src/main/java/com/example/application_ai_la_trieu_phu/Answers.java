package com.example.application_ai_la_trieu_phu;

public class Answers {
    private String content;
    private boolean isCorrect;

    public Answers(String content, boolean isCorrect) {
        this.content = content;
        this.isCorrect = isCorrect;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
