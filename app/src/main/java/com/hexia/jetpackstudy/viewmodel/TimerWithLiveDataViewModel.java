package com.hexia.jetpackstudy.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hexia.jetpackstudy.ThreadUtils;

import java.util.Timer;
import java.util.TimerTask;

public class TimerWithLiveDataViewModel extends ViewModel {

    private Timer mTimer;

    private MutableLiveData<Integer> mCurrentSecond;

    public MutableLiveData<Integer> getCurrentSecond() {
        if (mCurrentSecond == null) {
            mCurrentSecond = new MutableLiveData<>();
        }
        return mCurrentSecond;
    }

    public void startTiming() {
        if (mTimer == null) {
            getCurrentSecond().setValue(0);
            mTimer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    ThreadUtils.dispatchToMainThread(() -> getCurrentSecond().setValue(getCurrentSecond().getValue() + 1));
                }
            };
            mTimer.schedule(timerTask, 1000, 1000);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }
}
