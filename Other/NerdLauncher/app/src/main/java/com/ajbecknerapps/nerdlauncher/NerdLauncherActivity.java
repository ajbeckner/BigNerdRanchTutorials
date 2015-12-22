package com.ajbecknerapps.nerdlauncher;

import android.support.v4.app.Fragment;

/**
 * Created by AJ on 6/28/15.
 */
public class NerdLauncherActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new NerdLauncherFragment();
    }
}
