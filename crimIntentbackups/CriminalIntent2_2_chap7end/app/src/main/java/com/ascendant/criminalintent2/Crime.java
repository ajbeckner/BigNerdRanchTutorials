package com.ascendant.criminalintent2;

import java.util.UUID;

/**
 * Created by AJ on 10/15/14.
 */
public class Crime {

    private UUID mId;
    private String mTitle;

    public UUID getId() {
        return mId;
    }

    public String getTitle() {

        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Crime() {
        // Generate unique identifier
        mId = UUID.randomUUID();

    }
}

