// @todo Make MUCH CHANGE

package org.pentagonal.android.recipicious;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.pentagonal.android.recipicious.api.Api;
import org.pentagonal.android.recipicious.response.Post;
import org.pentagonal.android.recipicious.response.ResponseMapAble;

@SuppressWarnings({
    "deprecation",
    "StatementWithEmptyBody"
})

public class NavigationActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener
{
    private Worker worker;

    private TextView title;
    private TextView body;

    private ProgressDialog progressDialog;

    @Override
    final protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        init(new Worker(this));
    }

    private void init(Worker workerObject)
    {
        worker = workerObject;
        worker.setToolbar((Toolbar)findViewById(R.id.toolbar));
        worker.setMainView((LinearLayout)findViewById(R.id.content_view));
        worker.setDrawerLayout(
            (DrawerLayout)findViewById(R.id.drawer_layout)
        )
            .setDrawerListener(worker.createActionBarToggle());
        worker.getActionBarDrawerToggle().syncState();

        worker.getToolbar().setTitle("");

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // set checked
        navigationView.getMenu().getItem(1).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(1));

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onBackPressed()
    {
        if (worker.isDrawableOpen()) {
            worker.closeDrawer();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ContentActivity extends AsyncTask<String, Void, String>
    {
        Post post;
        Exception e;

        protected void onPreExecute()
        {
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Wait while loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            try {
                post = Api.getPost(2);
            } catch (Exception e) {
                this.e = e;
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            worker.showMainView();
            if (post != null) {
                String bodyString = "";
                ResponseMapAble bodyMapAble = (ResponseMapAble)post.get("body");
                if (bodyMapAble != null) {
                    bodyString = bodyMapAble.get("Must")
                        + "\n\n"
                        + bodyMapAble.get("Hello");
                }
                System.out.println(post.get("title"));
                title.setText((String)post.get("title"));
                body.setText(bodyString);
            } else {
                title.setText(e.getMessage());
            }

            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@Nullable MenuItem item)
    {
        if (item == null) {
            return false;
        }

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        worker.removeAllViews();
        switch (id) {
            case R.id.nav_exit:
                getParentActivityIntent()
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                return true;
            case R.id.nav_gallery:
                break;
            case R.id.nav_camera:
                RecyclerView view = new RecyclerView(this);
                view.addItemDecoration(new RecyclerView.ItemDecoration()
                {
                    /*@Override
                    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
                    {
                        super.onDraw(c, parent, state);
                    }*/
                });

                worker.inflate(R.layout.item_container);
                worker.hideMainView();

                title = (TextView)findViewById(R.id.textViewArticleTitle);
                body = (TextView)findViewById(R.id.textViewArticleText);
                new ContentActivity().execute();
        }

        return worker.closeDrawer(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);

        // comment in bottom
        return true;
    }
}

/*
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat
            .getActionView(
                menu.findItem(R.id.action_search)
            );

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        final TextView text = new TextView(this);

        linearLayout.addView(text);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                query = "You search : " + query;
                text.setText(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                text.setText(newText);
                return false;
            }
        });
 */