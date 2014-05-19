package com.leandrofantin.ejerciciomercadolibre;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.leandrofantin.ejerciciomercadolibre.fragments.DetailItemFragment;
import com.leandrofantin.ejerciciomercadolibre.fragments.ListItemFragment;
import com.leandrofantin.ejerciciomercadolibre.json.Product;



/**
 * @author lfantin (lea)
 * Actividad principal, encargada de manejar los frames, up navigation, y el action bar.
 * */


public class MainActivity extends ActionBarActivity implements ListItemFragment.OnFragmentInteractionListener, FragmentManager.OnBackStackChangedListener {

    private final static String TAG = MainActivity.class.getName();
    private SearchView searchView;
    private static final String ARG_QUERY = "query";
    private static final String ARG_MENU = "menu";

    private String lastQuery = null;
    private Boolean isUpEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        this.initSearchView((SearchView) MenuItemCompat.getActionView(searchItem));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackStackChanged() {
        isUpEnable = (getSupportFragmentManager().getBackStackEntryCount()>0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isUpEnable);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onFragmentInteraction(Product product) {

        Log.d(TAG, String.format("Select product: %s", product.toString()));
        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailItemFragment detailItemFragment = DetailItemFragment.newInstance(product.getTitle(),product.getPrice(),product.getSubtitle(),product.getThumbnail(),product.getAvailableQuantity(),product.getSoldQuantity(),product.getCondition());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        //transaction.setTransitionStyle(R.anim.animation);
        //transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout);
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.container, detailItemFragment, DetailItemFragment.TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    private void initSearchView(SearchView newSearchView)
    {
        this.searchView = newSearchView;
        if (lastQuery!=null)
            this.searchView.setQuery(lastQuery,false);
        // search hint
        this.searchView.setQueryHint(getString(R.string.action_search));
        // suggestion listeners
        searchView.setOnQueryTextListener(new OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                FragmentManager fragmentManager = getSupportFragmentManager();
                ListItemFragment listFragment = (ListItemFragment) fragmentManager.findFragmentByTag(ListItemFragment.TAG);
                //Entonces no existe el fragmento, lo creo
                if (listFragment==null) {
                    ListItemFragment listItems = ListItemFragment.newInstance(query);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.container, listItems,ListItemFragment.TAG);
                    //////////////////transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    //ya existe el fragmento, entonces actualizo la busqueda
                    listFragment.runSearch(query);
                }
                lastQuery = query;
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query)
            {
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_QUERY, lastQuery);
        outState.putBoolean(ARG_MENU,isUpEnable);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null) {
            lastQuery = savedInstanceState.getString(ARG_QUERY);
            isUpEnable = savedInstanceState.getBoolean(ARG_MENU);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isUpEnable);
        }
    }
}
