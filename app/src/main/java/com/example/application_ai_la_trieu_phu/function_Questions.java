package com.example.application_ai_la_trieu_phu;

import java.util.List;

public class function_Questions {

    public String quest;

    public List<function_Answers> list;

    public function_Questions( String quest, List<function_Answers> list) {
        quest = quest;
        this.list = list;
    }

    public function_Questions() {

    }


    public String getquest() {
        return quest;
    }

    public void setquest(String question) {
        quest = question;
    }

    public List<function_Answers> getList() {
        return list;
    }

    public void setList(List<function_Answers> list) {
        this.list = list;
    }
}
