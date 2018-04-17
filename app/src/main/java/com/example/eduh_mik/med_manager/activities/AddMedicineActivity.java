package com.example.eduh_mik.med_manager.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.base.BaseActivity;
import com.example.eduh_mik.med_manager.fragments.AddMedicineFragment;
import com.example.eduh_mik.med_manager.fragments.ConfirmMedicineFragment;
import com.example.eduh_mik.med_manager.interfaces.OnFragmentInteractionListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMedicineActivity extends BaseActivity implements OnFragmentInteractionListener{
    private FragmentTransaction ft;
    private FragmentManager fm;
    @BindView(R.id.frame) FrameLayout frame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        ButterKnife.bind(this);
        assert getSupportActionBar() != null;
        //getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fm = getSupportFragmentManager();
        replaceFragment(AddMedicineFragment.newInstance(),"Add Medicine");
    }
    public void replaceFragment(Fragment fragment, String title){
        setTitle(title);
        ft = fm.beginTransaction();
        ft.add(frame.getId(), fragment);
        ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.commit();
    }
    public Fragment getCurrentFragment(){
        return fm.findFragmentById(frame.getId());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if(getCurrentFragment() instanceof ConfirmMedicineFragment){
            List<Fragment> fragments = fm.getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if(TextUtils.equals(fragment.getClass().getSimpleName(),ConfirmMedicineFragment.class.getSimpleName())){
                        fm.beginTransaction().remove(fragment).commit();
                        setTitle("Add Medicine");
                    }
                }
            }
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
