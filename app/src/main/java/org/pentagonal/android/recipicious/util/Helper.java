package org.pentagonal.android.recipicious.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;
import org.pentagonal.android.recipicious.R;

/**
 * Created on Linux User : (snsv) on 7/14/17.
 * <p>
 * - Package : org.pentagonal.android.recipicious
 * - Project : Recipicious
 *
 * @author pentagonal
 * @since July, 14 2017
 */

@SuppressWarnings({
    "WeakerAccess",
    "UnusedReturnValue",
    "unused"
})
public class Helper
{
    final public static int INTENT_CLEAR_NEW = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK;

    public static String FIRST_TIME_SELECTOR = "isFirstTime";

    public static void startActivity(Activity activity, Class cls)
    {
        startActivity(activity, new Intent(activity, cls));
    }

    public static void startActivity(Activity activity, Class cls, int flags)
    {
        startActivity(activity, new Intent(activity, cls), flags);
    }

    public static void startActivity(Activity activity, Intent intent, int flags)
    {
        intent.setFlags(flags);
        startActivity(activity, intent);
    }

    public static void startActivity(Activity activity, Intent intent)
    {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void startActivities(Activity activity, Intent[] intent)
    {
        activity.startActivities(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void startActivities(Activity activity, Class[] intent)
    {
        if (intent.length < 1) {
            return;
        }
        Intent[] intents = {};
        for (int i = 0;intent.length > i;i++) {
            intents[i] = new Intent(activity, intent[i]);
        }
        activity.startActivities(intents);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static boolean postDelayed(Runnable r, long delayMillis)
    {
        return new Handler().postDelayed(r, delayMillis);
    }

    public static void makeText(Context context, int res, int duration)
    {
        Toast.makeText(context, res, duration).show();
    }

    public static void makeText(Context context, CharSequence charSequence, int duration)
    {
        Toast.makeText(context, charSequence, duration).show();
    }

    public static void makeTextLong(Context context, int res)
    {
        makeText(context, res, Toast.LENGTH_LONG);
    }

    public static void makeTextLong(Context context, CharSequence charSequence)
    {
        makeText(context, charSequence, Toast.LENGTH_LONG);
    }

    public static void makeTextShort(Context context, int res)
    {
        makeText(context, res, Toast.LENGTH_LONG);
    }

    public static void makeTextShort(Context context, CharSequence charSequence)
    {
        makeText(context, charSequence, Toast.LENGTH_LONG);
    }
}
