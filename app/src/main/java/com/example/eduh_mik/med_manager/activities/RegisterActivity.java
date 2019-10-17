package com.example.eduh_mik.med_manager.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.util.Collections.singleton;

public class RegisterActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = "Google SIgn In";
    private static final int OUR_REQUEST_CODE = 49404;
    public static String PHOTO_URL="";
    private GoogleApiClient mGoogleApiClient;
    @BindView(R.id.et_Email) EditText etEmail;
    @BindView(R.id.et_FirstName)
    EditText etFirstName;
    @BindView(R.id.et_LastName)
    EditText etLastName;
    @BindView(R.id.btn_Submit)
    AppCompatButton btnSubmit;
    @BindView(R.id.iv_ProfileImage)
    ImageView ivProfileImage;
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
        showSweetDialog("Register","Creating Account. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                _sweetAlertDialog.dismissWithAnimation();
                sharedPrefs.setIsloggedIn(true);
                showSweetDialog("Register","Account Created Successfully", SweetAlertDialog.SUCCESS_TYPE, "Got It!",new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        startNewActivity(MainActivity.class);
                        RegisterActivity.this.finish();
                    }
                });
            }
        }, 2000);
    }
    private static User addUser(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGoogleClient();
        setContentView(R.layout.activity_register);
        setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
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
    public void initGoogleClient(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(AppData.GOOGLE_SERVER_CLIENT_ID).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        mGoogleApiClient.connect();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            String permission = Manifest.permission.GET_ACCOUNTS;
            int grant = ContextCompat.checkSelfPermission(this, permission);
            if ( grant != PackageManager.PERMISSION_GRANTED) {
                PermissionManager permissionManager = PermissionManager.getInstance(this);
                permissionManager.checkPermissions(singleton(permission), new PermissionManager.PermissionRequestListener() {
                    @Override
                    public void onPermissionGranted() {
                        silentSignin();
                    }
                    @Override
                    public void onPermissionDenied() {
                        //showToast("Read Account Permission Denied");
                    }
                });
            }else{
                silentSignin();
            }
        }else{
            silentSignin();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, OUR_REQUEST_CODE);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            return;
        }
    }
    @SuppressLint("NewApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("Contacts","Register Fragment"+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OUR_REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    public void silentSignin(){
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            Log.d(TAG, "No cached sign-in");
            showProgressDialog("Signing in");
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    public void handleSignInResult(GoogleSignInResult result) {
        Log.e(TAG, "handleSignInResult:" + result.isSuccess()+":"+result.getStatus().toString());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e("",acct.getEmail());
            etEmail.setText(acct.getEmail());
            etFirstName.setText(acct.getGivenName());
            etLastName.setText(acct.getFamilyName());
            if(acct.getPhotoUrl() != null) {
                Glide.with(this).load(acct.getPhotoUrl().toString()).apply(RequestOptions.circleCropTransform()).into(ivProfileImage);
                PHOTO_URL = acct.getPhotoUrl().toString();
            }else{

            }
        } else {
            showToast("Sign In Failure");
        }
    }
}
