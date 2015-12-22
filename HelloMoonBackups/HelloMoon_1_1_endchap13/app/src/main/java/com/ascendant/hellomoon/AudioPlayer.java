package com.ascendant.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by AJ on 11/16/14.
 */
public class AudioPlayer {

    MediaPlayer mPlayer;

    public void stop(){
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
    public void play(Context context) {
        stop();

        mPlayer = MediaPlayer.create(context, R.raw.one_small_step);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });

        mPlayer.start();
    }
}
