package com.example.eduh_mik.med_manager.base;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.eduh_mik.med_manager.tools.SweetAlertDialog;
import com.example.eduh_mik.med_manager.sharedprefs.SharedPrefs;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {
    public SweetAlertDialog _sweetAlertDialog;
    public SharedPrefs sharedPrefs;
    public static Gson gson;

    public BaseFragment() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefs = new SharedPrefs(getActivity());
        gson = new Gson();
    }
    /**
     * show sweet alert with default duration
     * @param message to be displayed
     * @param title of alert
     * @param dialogType
     */
    public void showSweetDialog(String title, String message, int dialogType){
        _sweetAlertDialog = new SweetAlertDialog(getActivity(), dialogType);
        _sweetAlertDialog.setTitleText(title);
        _sweetAlertDialog.setContentText(message);
        _sweetAlertDialog.show();
    }
    /**
     * show sweet alert with default duration
     * @param message to be displayed
     * @param title of alert
     * @param dialogType
     * @param listener neutral button listener
     */
    public void showSweetDialog(String title, String message, int dialogType, String confirmText, SweetAlertDialog.OnSweetClickListener listener){
        _sweetAlertDialog = new SweetAlertDialog(getActivity(), dialogType);
        _sweetAlertDialog.setTitleText(title);
        _sweetAlertDialog.setContentText(message);
        _sweetAlertDialog.setConfirmText(confirmText);
        _sweetAlertDialog.setConfirmClickListener(listener);
        _sweetAlertDialog.show();
    }
    /**
     * show toast with default duration
     * @param message to be displayed
     */
    public void showToast(String message)
    {
        try {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void startNewActivity(Class<? extends Activity> clazz){
        startActivity(new Intent(getActivity(),clazz));
    }
}
