package com.example.eduh_mik.med_manager.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.appdata.AppData;
import com.example.eduh_mik.med_manager.base.BaseActivity;
import com.example.eduh_mik.med_manager.database.AppDatabase;
import com.example.eduh_mik.med_manager.fragments.ConfirmMedicineFragment;
import com.example.eduh_mik.med_manager.models.User;
import com.example.eduh_mik.med_manager.tools.SweetAlertDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.intentfilter.androidpermissions.PermissionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.util.Collections.singleton;

public class EditProfileActivity extends BaseActivity{
    public static String PHOTO_URL="";
    @BindView(R.id.et_Email) EditText etEmail;
    @BindView(R.id.et_FirstName)
    EditText etFirstName;
    @BindView(R.id.et_LastName)
    EditText etLastName;
    @BindView(R.id.iv_ProfileImage)
    ImageView ivProfileImage;
    @BindView(R.id.btn_Submit)
    AppCompatButton btnSubmit;
    @OnClick(R.id.btn_Submit)
    public void onViewClicked() {
        validate();
    }
    public void validate(){
        String email = etEmail.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            etEmail.requestFocus();
            etEmail.setError("Email cannot be empty");
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.requestFocus();
            etEmail.setError("Invalid email");
        } else if(TextUtils.isEmpty(firstName)){
            etFirstName.requestFocus();
            etFirstName.setError("First name cannot be empty");
        }else if(TextUtils.isEmpty(lastName)){
            etLastName.requestFocus();
            etLastName.setError("Last name cannot be empty");
        }else{
            register(email,firstName,lastName);
        }
    }
    public void register(String email,String firstName,String lastName){
        addUser(AppDatabase.getAppDatabase(this),new User(1,email,firstName,lastName,TextUtils.isEmpty(PHOTO_URL) ? "" : PHOTO_URL));
        showSweetDialog("Edit Profile","Saving Profile. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                _sweetAlertDialog.dismissWithAnimation();
                sharedPrefs.setIsloggedIn(true);
                showSweetDialog("Edit Profile","Profile Saved Succesfully", SweetAlertDialog.SUCCESS_TYPE, "Got It!",new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        startNewActivity(MainActivity.class);
                        EditProfileActivity.this.finish();
                    }
                });
            }
        }, 2000);
    }
    private static User addUser(final AppDatabase db, User user) {
        db.userDao().upDate(user);
        return user;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Edit Profile");
        ButterKnife.bind(this);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        User user = AppDatabase.getAppDatabase(this).userDao().findById(1);
        btnSubmit.setText("Save");
        etEmail.setText(user.getEmail());
        etFirstName.setText(user.getFirstName());
        etLastName.setText(user.getLastName());
        Glide.with(this).load(user.getImage()).apply(RequestOptions.circleCropTransform()).into(ivProfileImage);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    public void onPause() {
        super.onPause();
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
        super.onBackPressed();
    }
}
