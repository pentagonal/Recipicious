/*!
 * @status FIX
 */

package org.pentagonal.android.recipicious;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import org.pentagonal.android.recipicious.util.Helper;

@SuppressWarnings({
    "WeakerAccess",
    "unused"
})

public class MainActivity extends AppCompatActivity
{
    public static boolean isFirstStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // allow strict policy
        StrictMode.setThreadPolicy(
            new StrictMode.ThreadPolicy.Builder().permitAll().build()
        );

        Helper
            .startActivity(
                this,
                NavigationActivity.class,
                Helper.INTENT_CLEAR_NEW
            );
    }
}
