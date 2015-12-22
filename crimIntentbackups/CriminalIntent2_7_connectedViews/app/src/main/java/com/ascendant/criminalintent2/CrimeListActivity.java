package com.ascendant.criminalintent2;

import android.support.v4.app.Fragment;

/**
 * Created by AJ on 10/16/14.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
