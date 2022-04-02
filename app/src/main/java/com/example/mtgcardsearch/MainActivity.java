package com.example.mtgcardsearch;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.mtgcardsearch.databinding.ActivityMainBinding;
import com.example.mtgcardsearch.data.PrefDataStore;
import com.example.mtgcardsearch.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity
        extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private MenuItem searchMenuItem;
    private NavController navController;

    private PrefDataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home
                , R.id.nav_mydecks
                , R.id.nav_wishlist
                , R.id.nav_setlist)
                .setOpenableLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        BottomNavigationView navView = binding.appBarMain.bottomNavigation;
        NavigationUI.setupWithNavController(navView, navController);

        binding.appBarMain.fab.setOnClickListener(view -> searchMenuItem.expandActionView());

//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                searchMenuItem.expandActionView();
//
//                searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
//                    @Override
//                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
//                        Toast.makeText(MainActivity.this, "onMenuItemActionCollapse", Toast.LENGTH_SHORT).show();
//                        menuItem.collapseActionView();
//                        return false;
//                    }
//                });
//            }
//        });

        if (PrefDataStore.prefDataStore.dataStore == null)
            PrefDataStore.prefDataStore.setContext(getApplicationContext());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        searchMenuItem = menu.findItem( R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString("query", query);

                navController.navigate(R.id.nav_cardlist, bundle);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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
}