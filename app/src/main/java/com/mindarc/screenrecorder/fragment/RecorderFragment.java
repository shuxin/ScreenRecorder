package com.mindarc.screenrecorder.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mindarc.screenrecorder.Constants;
import com.mindarc.screenrecorder.R;
import com.mindarc.screenrecorder.RecorderModel;
import com.mindarc.screenrecorder.RecorderService;
import com.mindarc.screenrecorder.event.RecorderEvent;
import com.mindarc.screenrecorder.utils.LogUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by sean on 7/10/15.
 */
public class RecorderFragment extends Fragment implements View.OnClickListener {
    private final static String MODULE_TAG = "RecorderFragment";
    private Button mShutter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(MODULE_TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.recorder_fragment, container, false);
        mShutter = (Button) rootView.findViewById(R.id.shutter);
        mShutter.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.i(MODULE_TAG, "onAttach()");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.i(MODULE_TAG, "onDetach()");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        boolean isRecording = RecorderModel.getModel().isRecorderRunning();
        LogUtil.i(MODULE_TAG, "onClick() isRecording=" + isRecording);
        if (!isRecording) {
            mShutter.setText(R.string.shutter_starting);
            mShutter.setEnabled(false);
            start_rec();
            getActivity().finish();
            EventBus.getDefault().unregister(this);
        } else {
            mShutter.setText(R.string.shutter_stoping);
            mShutter.setEnabled(false);
            stop_rec();
        }
    }

    public void onEvent(RecorderEvent event) {
        LogUtil.i(MODULE_TAG, "onEvent isRecording:" + event.isRecording +
            ", fileName:" + event.fileName);
        mShutter.setEnabled(true);
        mShutter.setText(event.isRecording ? R.string.shutter_stop : R.string.shutter_start);
    }

    private void start_rec() {
        // Send init request
        Intent intent = new Intent(getActivity(), RecorderService.class);
        intent.setAction(Constants.Action.START_REC);
        intent.putExtra(Constants.Key.FILE_NAME, "/sdcard/test1.mp4");
        intent.putExtra(Constants.Key.TIME_LIMIT, 15);
        intent.putExtra(Constants.Key.WIDTH, 720);
        intent.putExtra(Constants.Key.HEIGHT, 1280);
        intent.putExtra(Constants.Key.BITRATE, 4000000);
        intent.putExtra(Constants.Key.ROTATE, false);
        getActivity().startService(intent);
    }

    private void stop_rec() {
        // Send init request
        Intent intent = new Intent(getActivity(), RecorderService.class);
        intent.setAction(Constants.Action.STOP_REC);
        getActivity().startService(intent);
    }

}
