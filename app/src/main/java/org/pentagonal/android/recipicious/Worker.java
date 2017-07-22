package org.pentagonal.android.recipicious;

import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created on Linux User : (snsv) on 7/18/17.
 * <p>
 * - Package : org.pentagonal.android.recipicious
 * - Project : Recipicious
 *
 * @author pentagonal
 * @since July, 18 2017
 */

@SuppressWarnings({
    "WeakerAccess",
    "UnusedReturnValue",
    "unused"
})

public class Worker extends AppCompatActivity
{
    private AppCompatActivity context;
    private ViewGroup mainView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    Worker(AppCompatActivity context)
    {
        this.context = context;
    }

    public ViewGroup setMainView(ViewGroup view)
    {
        return this.mainView = view;
    }

    public Toolbar setToolbar(Toolbar toolbar)
    {
        this.toolbar = toolbar;
        context.setSupportActionBar(this.getToolbar());
        return this.toolbar;
    }

    public DrawerLayout setDrawerLayout(DrawerLayout drawerLayout)
    {
        return this.drawerLayout = drawerLayout;
    }

    public ActionBarDrawerToggle createActionBarToggle()
    {
        return createActionBarToggle(false);
    }

    public ActionBarDrawerToggle createActionBarToggle(boolean force)
    {
        if (force || this.actionBarDrawerToggle == null) {
            this.actionBarDrawerToggle = new ActionBarDrawerToggle(
                (Activity)this.getContext(),
                this.getDrawerLayout(),
                this.getToolbar(),
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            );
        }

        return this.actionBarDrawerToggle;
    }

    public Toolbar getToolbar()
    {
        return this.toolbar;
    }

    @SuppressWarnings("unchecked")
    public <E> E getContext()
    {
        return (E) this.context;
    }

    public DrawerLayout getDrawerLayout()
    {
        return drawerLayout;
    }

    public <E> E closeDrawer(E result)
    {
        if (this.isDrawableOpen()) {
            this.getDrawerLayout().closeDrawer(GravityCompat.START);
        }

        return result;
    }

    public boolean closeDrawer()
    {
        return this.closeDrawer(true);
    }

    public boolean isDrawableOpen(int drawerGravity)
    {
        return this.getDrawerLayout().isDrawerOpen(drawerGravity);
    }

    public boolean isDrawableOpen()
    {
        return this.isDrawableOpen(GravityCompat.START);
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle()
    {
        return actionBarDrawerToggle;
    }

    public ViewGroup getMainView()
    {
        return mainView;
    }

    public void hideMainView()
    {
        this.getMainView().setVisibility(View.GONE);
    }

    public void showMainView()
    {
        this.getMainView().setVisibility(View.VISIBLE);
    }

    public void removeAllViews()
    {
        this.getMainView().removeAllViews();
    }

    public View inflate(int res)
    {
        return this.context
            .getLayoutInflater()
            .inflate(res, this.getMainView());
    }
}
