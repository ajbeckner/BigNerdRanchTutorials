package com.ajbecknerapps.runtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.location.Location;

/**
 * Created by AJ on 7/12/15.
 */
public class TrackingLocationReceiver extends LocationReceiver {
    @Override
    protected void onLocationReceived(Context c, Location loc) {
        RunManager.get(c).insertLocation(loc);
    }
}
