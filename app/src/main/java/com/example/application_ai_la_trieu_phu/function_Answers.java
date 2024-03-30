package com.example.application_ai_la_trieu_phu;

public class function_Answers {
    public String answers;
    public boolean check;

    public function_Answers(String answers, boolean check) {
        this.answers = answers;
        this.check = check;
    }

    public function_Answers() {
    }


    public String getanswers() {
        return answers;
    }

    public void setanswers(String content) {
        this.answers = content;
    }

    public boolean getcheck() {
        return check;
    }

    public void setcheck(boolean check) {
        check = check;
    }
}
