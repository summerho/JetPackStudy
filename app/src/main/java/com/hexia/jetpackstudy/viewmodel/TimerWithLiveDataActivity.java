package com.hexia.jetpackstudy.viewmodel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.hexia.jetpackstudy.R;

public class TimerWithLiveDataActivity extends AppCompatActivity {

    private TextView mTimeTv;

    private TimerWithLiveDataViewModel mTimerWithLiveDataViewModel;

    private MutableLiveData<Integer> mLiveData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_live_data);
        initView();
        initComponent();
    }

    private void initView() {
        mTimeTv = findViewById(R.id.time_tv);
        Button mStartBtn = findViewById(R.id.start_btn);
        Button mResetBtn = findViewById(R.id.reset_btn);
        mResetBtn.setOnClickListener(v -> mLiveData.setValue(0));
        mStartBtn.setOnClickListener(v -> mTimerWithLiveDataViewModel.startTiming());
    }

    private void initComponent() {
        mTimerWithLiveDataViewModel = new ViewModelProvider(this).get(TimerWithLiveDataViewModel.class);
        mLiveData = mTimerWithLiveDataViewModel.getCurrentSecond();
        mLiveData.observe(this, integer -> mTimeTv.setText(String.valueOf(integer)));
    }
}
