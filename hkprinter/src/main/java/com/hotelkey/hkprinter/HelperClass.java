package com.hotelkey.hkprinter;

import android.content.res.Resources;

public class HelperClass {

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

}
