package com.sdsmdg.kd.trianglify.utilities;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * <h1>Title</h1>
 * <b>Description :</b>
 * <p>
 *
 * @author suyash
 * @since 5/5/17.
 */

public class Utilities {
    public int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public int pxToDp(int px, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
