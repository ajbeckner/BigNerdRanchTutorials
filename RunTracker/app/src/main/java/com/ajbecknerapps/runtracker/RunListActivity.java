package com.ajbecknerapps.runtracker;

import android.support.v4.app.Fragment;

/**
 * Created by AJ on 7/12/15.
 */
public class RunListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new RunListFragment();
    }
}
