/*!
 * @status FIX
 */

package org.pentagonal.android.recipicious;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.support.annotation.Nullable;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import org.pentagonal.android.recipicious.util.Helper;

public class IntroActivity extends AppIntro
{
    private boolean doubleBackToExitPressedOnce = false;
    private SharedPreferences sharedPreferences;
    private int[] drawables ={
        R.drawable.ic_lightbulb_outline_white_240dp,
        R.drawable.ic_watch_later_white_240dp,
        R.drawable.ic_verified_user_white_240dp,
        R.drawable.ic_check_white_240dp,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(getBaseContext());
        if (sharedPreferences.getBoolean(Helper.FIRST_TIME_SELECTOR, true)) {
            MainActivity.isFirstStart = true;
            String[] titles = getResources().getStringArray(R.array.intro_title);
            String[] descriptions = getResources().getStringArray(R.array.intro_description);
            String[] bgColor = getResources().getStringArray(R.array.intro_color);
            for (int i = 0; i < titles.length; i++) {
                addSlide(
                    AppIntroFragment
                        .newInstance(
                            titles[i],
                            descriptions[i],
                            drawables[i],
                            Color.parseColor(bgColor[i])
                        )
                );
            }

            showStatusBar(false);
            showSkipButton(true);
            // askForPermissions(permissions, 1);
        } else {
            launchHome();
        }
    }

    private void launchHome()
    {
        if (sharedPreferences.getBoolean(Helper.FIRST_TIME_SELECTOR, true)) {
            sharedPreferences.edit().putBoolean(Helper.FIRST_TIME_SELECTOR, false).apply();
        }

        Helper
            .startActivity(
                this,
                new Intent(IntroActivity.this, MainActivity.class).setAction(Intent.ACTION_MAIN),
                Helper.INTENT_CLEAR_NEW
            );

        finish(); // Call once you redirect to another activity
    }

    @Override
    public void onDonePressed(Fragment currentFragment)
    {
        super.onDonePressed(currentFragment);
        launchHome();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment)
    {
        super.onSkipPressed(currentFragment);
        launchHome();
    }

    @Override
    public void onBackPressed()
    {
        if (!pager.isFirstSlide(fragments.size())) {
            pager.goToPreviousSlide();
        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Helper
                .makeTextShort(this, R.string.language_back_exit);
            Helper.postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment)
    {
        super.onSlideChanged(oldFragment, newFragment);
    }

}