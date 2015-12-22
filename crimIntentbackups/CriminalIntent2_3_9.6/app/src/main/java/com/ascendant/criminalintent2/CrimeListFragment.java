package com.ascendant.criminalintent2;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;

/**
 * Created by AJ on 10/16/14.
 */
public class CrimeListFragment extends ListFragment {
    private ArrayList<Crime> mCrimes;

     @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crimes_title);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();
     }
}
