package com.fiap.heitor.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fiap.heitor.android.R;
import com.fiap.heitor.android.adapter.LocationAdapter;
import com.fiap.heitor.android.decorator.AboutApp;
import com.fiap.heitor.android.decorator.DialogDecorator;
import com.fiap.heitor.android.exception.FiapDatabaseException;
import com.fiap.heitor.android.model.Place;
import com.fiap.heitor.android.model.User;
import com.fiap.heitor.android.persistence.DAO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationAdapter.OnItemRecyclerClickLister, DialogDecorator.OnDeleteListener {

    @BindView(R.id.location_list)
    RecyclerView mLocationList;

    private LocationAdapter mAdapter;
    private List<Place> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupRecyclerView();
    }


    private void setupRecyclerView() {
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        mLocationList.setLayoutManager(llManager);
        mAdapter = new LocationAdapter(mData, this, this);
        mLocationList.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDataSource();
    }

    private void updateDataSource() {
        setupRecyclerView();
        List<Place> refreshData = DAO.getInstance(this).listAll();
        mAdapter.updateData(refreshData);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.new_location) {
            newLocation("");
        } else if (id == R.id.about) {
            AboutApp aboutApp = new AboutApp(this);
            aboutApp.decorate();
        } else if (id == R.id.logout) {
            logout();
        } else if (id == R.id.leave) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void edit(Place place) {
        Toast.makeText(this, "Edit = " + place.getFeatureName(), Toast.LENGTH_SHORT).show();
        newLocation(place.getId());
    }

    @Override
    public void delete(Place place) {
        DialogDecorator dialogDecorator = new DialogDecorator(this, this, place.getId());
        dialogDecorator.decorate();
    }

    private void newLocation(String id) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (id != null && !id.isEmpty()) {
            intent.putExtra(RegisterActivity.PLACE_ID_PARAMS, id);
        }
        startActivity(intent);
    }

    @Override
    public void deletePlace(String id) {
        DAO.getInstance(this).delete(id);
        updateDataSource();
    }

    private void logout() {
        User user = DAO.getInstance(this).findOneUser();
        if (user != null) {
            try {
                DAO.getInstance(this).deleteUser(user);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } catch (FiapDatabaseException e) {
                e.printStackTrace();
            }
        }

    }


}
