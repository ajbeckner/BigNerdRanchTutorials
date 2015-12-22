package com.ajbecknerapps.photogallery;

import android.support.v4.app.Fragment;

/**
 * Created by AJ on 7/11/15.
 */
public class PhotoPageActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new PhotoPageFragment();
    }
}
