package com.ascendant.hellomoon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by AJ on 11/16/14.
 */
public class HelloMoonFragment extends Fragment {

    private Button mPlayButton;
    private Button mStopButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hello_moon,parent,false);

        mPlayButton = (Button)v.findViewById(R.id.hello_moon_playbutton);
        mStopButton = (Button)v.findViewById(R.id.hello_moon_stopbutton);

        return v;

    }
}
