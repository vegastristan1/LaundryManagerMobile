package com.example.laundrymanagermobile.ui.profits;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfitsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProfitsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is profit fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}