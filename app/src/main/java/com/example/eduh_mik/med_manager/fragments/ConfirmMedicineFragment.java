package com.example.eduh_mik.med_manager.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eduh_mik.med_manager.AlarmService;
import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.activities.MainActivity;
import com.example.eduh_mik.med_manager.activities.RegisterActivity;
import com.example.eduh_mik.med_manager.application.AppController;
import com.example.eduh_mik.med_manager.base.BaseFragment;
import com.example.eduh_mik.med_manager.database.AppDatabase;
import com.example.eduh_mik.med_manager.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.med_manager.models.Medicine;
import com.example.eduh_mik.med_manager.models.User;
import com.example.eduh_mik.med_manager.tools.SweetAlertDialog;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfirmMedicineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmMedicineFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MEDICINE = "medicine";
    @BindView(R.id.tv_Name) TextView tvName;
    @BindView(R.id.tv_Ailment) TextView tvAilment;
    @BindView(R.id.tv_Description) TextView tvDescription;
    @BindView(R.id.tv_StartDate) TextView tvStartDate;
    @BindView(R.id.tv_EndDate) TextView tvEndDate;
    @OnClick(R.id.btn_Edit)
    public void onBtnEditClicked() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.btn_Confirm)
    public void onBtnConfirmClicked() {
        addMedicine();
    }
    public void addMedicine(){
        AppDatabase.getAppDatabase(getActivity()).medicineDao().insertAll(medicine);
        showSweetDialog("Medicine","Adding Medicine. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                _sweetAlertDialog.dismissWithAnimation();
                sharedPrefs.setIsloggedIn(true);
                showSweetDialog("Medicine","Medicine Added Succesfully", SweetAlertDialog.SUCCESS_TYPE, "Got It!",new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        getActivity().startService(new Intent(getActivity(),AlarmService.class));
                        getActivity().finish();
                    }
                });
            }
        }, 2000);
    }
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private static Medicine medicine;

    private OnFragmentInteractionListener mListener;

    public ConfirmMedicineFragment() {
        // Required empty public constructor
    }

    public static ConfirmMedicineFragment newInstance(Medicine medicine) {
        ConfirmMedicineFragment fragment = new ConfirmMedicineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEDICINE, gson.toJson(medicine));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            medicine = gson.fromJson(getArguments().getString(ARG_MEDICINE), Medicine.class);
            Log.e("Medicine", gson.toJson(medicine));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName.setText(medicine.getName());
        tvAilment.setText(medicine.getAilment());
        tvDescription.setText(medicine.getDescription());
        tvStartDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(medicine.getStartDate()));
        tvEndDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(medicine.getEndDate()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_medicine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
