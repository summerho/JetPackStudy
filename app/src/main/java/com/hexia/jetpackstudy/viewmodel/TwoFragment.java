package com.hexia.jetpackstudy.viewmodel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hexia.jetpackstudy.R;

public class TwoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_two, container, false);
        SeekBar seekBar = parentView.findViewById(R.id.seekbar);
        ShareDataViewModel shareDataViewModel = new ViewModelProvider(getActivity()).get(ShareDataViewModel.class);
        MutableLiveData<Integer> liveData = shareDataViewModel.getProgress();
        liveData.observe(getViewLifecycleOwner(), integer -> seekBar.setProgress(integer));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                liveData.setValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return parentView;
    }
}
