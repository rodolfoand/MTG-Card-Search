package com.example.mtgcardsearch;

import android.app.SearchManager;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.MenuItem;
import android.view.Menu;

import com.example.mtgcardsearch.databinding.ActivityMainBinding;
import com.example.mtgcardsearch.data.PrefDataStore;
import com.example.mtgcardsearch.model.AutocompSearchResult;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity
        extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private MenuItem searchMenuItem;
    private NavController navController;
    private MainViewModel mainViewModel;
    private LifecycleOwner lifecycleOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (PrefDataStore.prefDataStore.dataStore == null)
            PrefDataStore.prefDataStore.setContext(getApplicationContext());

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        lifecycleOwner = this;

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home
                , R.id.nav_decklist
                , R.id.nav_wishlist)
                .setOpenableLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        BottomNavigationView navView = binding.appBarMain.bottomNavigation;
        NavigationUI.setupWithNavController(navView, navController);

        binding.appBarMain.fab.setOnClickListener(view -> searchMenuItem.expandActionView());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        searchMenuItem = menu.findItem( R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        final CursorAdapter suggestionAdapter = new SimpleCursorAdapter(this,
                R.layout.item_list_suggestion,
                null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{android.R.id.text1},
                0);

        final List<String> suggestions = new ArrayList<>();

        searchView.setSuggestionsAdapter(suggestionAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                searchView.setQuery(suggestions.get(position), false);
                searchView.clearFocus();
                doSearchbyName(suggestions.get(position));
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mainViewModel.getNames(newText).observe(lifecycleOwner, new Observer<AutocompSearchResult>() {
                    @Override
                    public void onChanged(AutocompSearchResult autocompSearchResult) {
                        suggestions.clear();
                        suggestions.addAll(autocompSearchResult.getData());

                        String[] columns = {
                                BaseColumns._ID,
                                SearchManager.SUGGEST_COLUMN_TEXT_1,
                                SearchManager.SUGGEST_COLUMN_INTENT_DATA
                        };
                        MatrixCursor cursor = new MatrixCursor(columns);

                        for (int i = 0; i < autocompSearchResult.getData().size(); i++) {
                            String[] tmp = {Integer.toString(i), autocompSearchResult.getData().get(i), autocompSearchResult.getData().get(i)};
                            cursor.addRow(tmp);
                        }
                        suggestionAdapter.swapCursor(cursor);
                    }
                });
                return false;
            }
        });

        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                if (getSupportFragmentManager().getFragments().size() > 1) onBackPressed();
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_about:
                navController.navigate(R.id.nav_about);
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void doSearch(String query){
        Bundle bundle = new Bundle();
        bundle.putString("query", query);
        navController.navigate(R.id.nav_cardlist, bundle);

    }

    private void doSearchbyName(String name){
        Bundle bundle = new Bundle();
        bundle.putString("fuzzy", name);
        navController.navigate(R.id.nav_cardlist, bundle);
    }
}