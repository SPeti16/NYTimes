package com.work.wup.nytimes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;



//Ez az activity, a felelős hogy az alkalmazás jól működhessen.


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String TAG = MainActivity.class.getSimpleName();
    private String KEY ="d571bad2d4a7437b9d2ad0fe02e0d64d";
    private int days=1;
    private News[] news;
    NavigationView navigationView;
    Toolbar toolbar;
    MaterialSearchView searchView;
    String searchText="";
    Boolean searchSubmit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_title);

        searchView = findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchSubmit=false;
                searchView.setQuery(searchText,false);
            }

            @Override
            public void onSearchViewClosed() {
                if(searchText.equals("")){
                    ((fragmentNews)fragmentManager.findFragmentById(R.id.fragment)).refresh();}
            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchSubmit=true;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!searchSubmit) {
                    searchText = newText;
                }
                if(!newText.equals(""))
                {((fragmentNews)fragmentManager.findFragmentById(R.id.fragment)).search(newText);}
                else{
                    if(!searchSubmit){
                    ((fragmentNews)fragmentManager.findFragmentById(R.id.fragment)).refresh();}
                }

                return false;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_day);

        navigationView.getMenu().findItem(R.id.nav_day).setEnabled(false);
        navigationView.getMenu().findItem(R.id.nav_week).setEnabled(false);
        navigationView.getMenu().findItem(R.id.nav_month).setEnabled(false);


        news=new News[3];


        fragmentManager = getSupportFragmentManager();
        openFragment(fragmentDownloadData.class.getName(),new fragmentDownloadData());

    }

    //Új fragmentek megnyitására szolgáló metódus.
    public void openFragment(String classname, Fragment fragmentname){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(classname);
        fragmentTransaction.replace(R.id.fragment,fragmentname);
        fragmentTransaction.commit();

        menuEnable(classname.equals(fragmentNews.class.getName()));

        if(classname.equals(fragmentWeb.class.getName())){
            toolbar.getMenu().findItem(R.id.action_browser).setVisible(true);
            toolbar.getMenu().findItem(R.id.action_search).setVisible(false);
        }
    }

    //Erre a fragmentWeb-nek van szüksége, hogy megkaphassa az url-t.
    public void sendDataFragment(String classname, Fragment fragmentname, Bundle bundle){
        fragmentname.setArguments(bundle);
        openFragment(classname, fragmentname);
    }

    //Json adatok letöltésének megkezdése.
    public void startDownload(){
        new GetContacts().execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            String fragmentName=fragmentManager.findFragmentById(R.id.fragment).getClass().getName();
            if(fragmentName.equals(fragmentWeb.class.getName())) {
                menuEnable(true);
                toolbar.getMenu().findItem(R.id.action_browser).setVisible(false);
                toolbar.getMenu().findItem(R.id.action_search).setVisible(true);
                super.onBackPressed();
            }else{
                if(fragmentName.equals(fragmentNews.class.getName())){
                    startExit();
                }
            }
        }
    }

    private void menuEnable(Boolean enable){
        if (enable) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    navigationView.getMenu().findItem(R.id.nav_day).setEnabled(true);
                    navigationView.getMenu().findItem(R.id.nav_week).setEnabled(true);
                    navigationView.getMenu().findItem(R.id.nav_month).setEnabled(true);
                    toolbar.getMenu().findItem(R.id.action_search).setVisible(true);
                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    navigationView.getMenu().findItem(R.id.nav_day).setEnabled(false);
                    navigationView.getMenu().findItem(R.id.nav_week).setEnabled(false);
                    navigationView.getMenu().findItem(R.id.nav_month).setEnabled(false);
                }
            });
        }
    }

    private void startExit(){
        new Exit().showDialogExit(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_exit) {
            startExit();
            return true;
        }

        if (id == R.id.action_browser) {
            ((fragmentWeb)fragmentManager.findFragmentById(R.id.fragment)).openBrowser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_day) {
            days=0;
        } else if (id == R.id.nav_week) {
            days=1;
        } else if (id == R.id.nav_month) {
            days=2;
        }

        refreshNews();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Article[] getArticle(){
        return news[days].getArticles();
    }

    //A cikk listának a frissítésére szolgál.
    private void refreshNews(){
            ((fragmentNews)fragmentManager.findFragmentById(R.id.fragment)).refresh();
    }

    //Json adatok letöltése után itt kezdi meg azoknak a feldolgozását.
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String url;
            fragmentDownloadData loadingF = (fragmentDownloadData)fragmentManager.findFragmentById(R.id.fragment);
            for(int i=0;i<3;i++) {
                switch (i){
                    case 0: days=1; break;
                    case 1: days=7; break;
                    case 2: days=30; break;
                }
                url = "http://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/" + String.valueOf(days)
                        + ".json?api-key=" + KEY;

                Log.e(TAG, "Response from url: " + sh.makeServiceCall(url));
                news[i] = new News(sh.makeServiceCall(url));

                loadingF.addDownloadCounter();

            }
            days=0;
            openFragment(fragmentNews.class.getName(),new fragmentNews());

            return null;
        }
    }

}
