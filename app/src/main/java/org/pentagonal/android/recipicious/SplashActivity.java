/*!
 * @status FIX
 */

package org.pentagonal.android.recipicious;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import org.pentagonal.android.recipicious.util.Helper;

public class SplashActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bounceDown((TextView) findViewById(R.id.splash_screen_text));
    }

    private void bounceDown(final TextView textView)
    {
        final Animation animBounce = AnimationUtils.loadAnimation(this, R.anim.bounce_down);
        final Animation animBounceDown = AnimationUtils.loadAnimation(this, R.anim.mini_scale);

        animBounce.setDuration(1000);
        animBounceDown.setDuration(1000);

        animBounce.setFillAfter(true);
        animBounce.setFillEnabled(true);

        textView.startAnimation(animBounce);
        animBounceDown.setAnimationListener(new Animation.AnimationListener()  {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationRepeat(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation)
                {
                    // set on end
                    textView.setText("");
                    textView.clearAnimation();

                    Helper
                        .startActivity(
                            SplashActivity.this,
                            (PreferenceManager
                                .getDefaultSharedPreferences(getBaseContext())
                                .getBoolean(Helper.FIRST_TIME_SELECTOR, true)
                            )
                            ? IntroActivity.class
                            : MainActivity.class,
                            Helper.INTENT_CLEAR_NEW
                        );
                    finish();
                }
            }
        );

        animBounce.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(final Animation animation)
            {
                Helper.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        textView.startAnimation(animBounceDown);
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
    }
}
