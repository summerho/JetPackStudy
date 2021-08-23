package com.hexia.jetpackstudy.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareDataViewModel extends ViewModel {

    private MutableLiveData<Integer> mProgress;

    public MutableLiveData<Integer> getProgress() {
        if (mProgress == null) {
            mProgress = new MutableLiveData<>();
        }
        return mProgress;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mProgress = null;
    }
}
