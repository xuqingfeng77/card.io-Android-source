package com.paypal.android.sdk.common.ui;

/* ActivityHelper.java
 * See the file "LICENSE.md" for the full license governing this code.
 */

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class ActivityHelper {

    public static void animateActivityStart(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
        // TODO - animate via overridePendingTransition if possible
    }

    public static void animateActivityBack(Activity activity) {
        // TODO - animate via overridePendingTransition if possible
    }

    /**
     * Request the ActionBar window feature if we are on a supported Android
     * version. This should be called before the activity's setContentView. See
     * also {@link #setupActionBarIfSupported(Activity, int)}, which sets the
     * ActionBar appearance.
     *
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void addActionBarIfSupported(Activity activity) {
        if (actionBarSupported()) {
            activity.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        }
    }

    /**
     * Sets up the title, appearance, and behavior of the ActionBar if it is
     * supported. Should be invoked after
     * {@link #addActionBarIfSupported(Activity)}.
     *
     * @param activity
     * @param titleTextView
     * @param title
     * @param titleTextViewPrefix
     * @param icon
     */
    public static void setupActionBarIfSupported(Activity activity, TextView titleTextView,
            String title, String titleTextViewPrefix, Drawable icon) {
        if (titleTextViewPrefix == null) {
            titleTextViewPrefix = "";
        }
        activity.setTitle(titleTextViewPrefix + title);
        if (actionBarSupported()) {
            setupActionBar(activity, title, icon);
            if (titleTextView != null) {
                titleTextView.setVisibility(View.GONE);
            }
        } else {
            if (titleTextView != null) {
                titleTextView.setText(title);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void setupActionBar(Activity activity, String title, Drawable icon) {
        ActionBar bar = activity.getActionBar();

        bar.setBackgroundDrawable(Appearance.ACTIONBAR_BACKGROUND);
        bar.setTitle(title);

        // Hack to set the actionbar text to white
        int actionBarTitleId =
                Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTextView = (TextView) activity.findViewById(actionBarTitleId);
        if (actionBarTextView != null) {
            actionBarTextView.setTextColor(Color.WHITE);
        }

        bar.setDisplayHomeAsUpEnabled(false);
        if (icon != null
                && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setActionBarHomeIcon(bar, icon);
            // bar.setDisplayHomeAsUpEnabled(true);
        } else {
            bar.setDisplayShowHomeEnabled(false);
        }
    }

    public static boolean actionBarSupported() {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void setActionBarHomeIcon(ActionBar bar, Drawable icon) {
        bar.setIcon(icon);
    }

    public static boolean holoSupported() {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Overrides whatever theme the activity currently has with either
     * Theme_Holo_Light or Theme_Light, depending on OS support.
     *
     * @param activity
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setActivityTheme(Activity activity) {
        if (holoSupported()) {
            activity.setTheme(android.R.style.Theme_Holo_Light);
        } else {
            activity.setTheme(android.R.style.Theme_Light);
        }
    }

}
