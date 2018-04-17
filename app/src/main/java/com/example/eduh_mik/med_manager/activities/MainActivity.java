package com.example.eduh_mik.med_manager.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.adapters.MainPagerAdapter;
import com.example.eduh_mik.med_manager.base.BaseActivity;
import com.example.eduh_mik.med_manager.database.AppDatabase;
import com.example.eduh_mik.med_manager.fragments.ActivityFragment;
import com.example.eduh_mik.med_manager.fragments.IntakeFragment;
import com.example.eduh_mik.med_manager.fragments.MedicineFragment;
import com.example.eduh_mik.med_manager.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.med_manager.models.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,OnFragmentInteractionListener,SearchView.OnQueryTextListener{

    public SearchView searchView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navView;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.container) ViewPager mViewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @OnClick(R.id.fab)
    public void onFabClicked() {
        startNewActivity(AddMedicineActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle("Med - Manager");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        loadViewPager();
    }
    public void loadViewPager(){
        ArrayList<Fragment> mFragments = new ArrayList<>();
        mFragments.add(IntakeFragment.newInstance());
        mFragments.add(ActivityFragment.newInstance());
        mFragments.add(MedicineFragment.newInstance());
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mainPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        User user = AppDatabase.getAppDatabase(this).userDao().findById(1);
        View headerView = navView.getHeaderView(0);
        TextView tvEmail = (TextView) headerView.findViewById(R.id.tv_Email);
        TextView tvName = (TextView) headerView.findViewById(R.id.tv_Name);
        ImageView ivImage = (ImageView) headerView.findViewById(R.id.iv_ProfileImage);
        tvName.setText(user.getFirstName()+" "+user.getLastName());
        tvEmail.setText(user.getEmail());
        Glide.with(this).load(user.getImage()).apply(RequestOptions.circleCropTransform()).into(ivImage);
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
        getMenuInflater().inflate(R.menu.main, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search Medicine");
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(2);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                search("");
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onQueryTextChange(String query) {
        search(query);
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        search(query);
        return false;
    }
    public void search(String string){
        onMedicineSearchListener.onMedicineSearched(string);
    }
    public static void initOnMedicineSearchListener(OnMedicineSearchListener _onMedicineSearchListener){
        onMedicineSearchListener = _onMedicineSearchListener;
    }
    private static OnMedicineSearchListener onMedicineSearchListener;
    public interface OnMedicineSearchListener{
        void onMedicineSearched(String name);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

         if(id == R.id.action_log_out){
            logOut();
        }

        return super.onOptionsItemSelected(item);
    }
    public void logOut(){
        sharedPrefs.setIsloggedIn(false);
        AppDatabase.getAppDatabase(this).userDao().delete(AppDatabase.getAppDatabase(this).userDao().findById(1));
        startNewActivity(SplashActivity.class);
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit) {
            startNewActivity(EditProfileActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
