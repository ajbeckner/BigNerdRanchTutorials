package com.ascendant.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by AJ on 11/16/14.
 */
public class SoundTestFragment extends Fragment {

    MediaPlayer mPlayer;

    private Button mPlayButton;
    int currentResId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hello_moon,parent,false);

        mPlayButton = (Button)v.findViewById(R.id.soundtest_playbutton);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                soundAction(getActivity(), R.raw.apollo_17_stroll);
            }
        });

        return v;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }    }
    public void soundAction(Context context, int resId) {

        if (resId == currentResId) {
            if (mPlayer != null) {
                mPlayer.release();
                mPlayer = null;
            }            currentResId = 0;
        } else {
            mPlayer = MediaPlayer.create(context, resId);

            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mPlayer != null) {
                        mPlayer.release();
                        mPlayer = null;
                    }                    currentResId = 0;
                }
            });
            currentResId = resId;
            mPlayer.start();
        }


    }
}
