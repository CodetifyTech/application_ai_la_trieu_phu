package com.example.application_ai_la_trieu_phu.tickclock;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimeCounter extends ViewModel {
    private int second = 60;

    private MutableLiveData<Integer> secondLiveData = new MutableLiveData<>();

    public LiveData<Integer> timeRemaining(){
        return secondLiveData;
    }
    public void timeTick(){
        second--;
        secondLiveData.setValue(second);
    }

    public int getTime(){
        return second;
    }
}
