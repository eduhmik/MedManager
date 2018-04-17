package com.example.eduh_mik.med_manager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.adapters.DoseAdapter;
import com.example.eduh_mik.med_manager.database.AppDatabase;
import com.example.eduh_mik.med_manager.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.med_manager.models.Dose;
import com.example.eduh_mik.med_manager.models.Medicine;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.Hours;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IntakeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntakeFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    private DoseAdapter doseAdapter;
    private ArrayList<Dose> doseItems = new ArrayList<>();
    private DividerItemDecoration _dividerItemDecoration;
    public IntakeFragment() {
        // Required empty public constructor
    }

    public static IntakeFragment newInstance() {
        return new IntakeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intake, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        _dividerItemDecoration=new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(_dividerItemDecoration);
        doseAdapter = new DoseAdapter(getActivity(), doseItems);
        recyclerView.setAdapter(doseAdapter);
        loadMedicineItems();
    }
    public void loadMedicineItems(){
        doseItems.clear();
        for(Medicine medicine: AppDatabase.getAppDatabase(getActivity()).medicineDao().getAll()){
            Calendar calendarPast = Calendar.getInstance();
            Calendar calendarRemaining = Calendar.getInstance();
            calendarPast.setTime(medicine.getStartDate());
            calendarRemaining.setTime(medicine.getEndDate());
            DateTime dtPast = new DateTime(calendarPast);
            DateTime dtRemaining = new DateTime(calendarRemaining);
            Hours hoursTotal = Hours.hoursBetween(dtPast,dtRemaining);
            for(int i = 0; i<hoursTotal.getHours(); i++){
                Calendar cal = Calendar.getInstance();
                cal.setTime(dtPast.plusHours(i).toDate());
                if (isToday(cal) && i%(24/medicine.getFrequency()) == 0){
                    doseItems.add(new Dose(medicine,dtPast.plusHours(i).toDate()));
                }
            }
        }
        Collections.sort(doseItems);
        doseAdapter.notifyDataSetChanged();
    }
    public static boolean isToday(Calendar cal) {
        return isSameDay(cal, Calendar.getInstance());
    }
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
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
    public void onResume() {
        super.onResume();
        loadMedicineItems();
    }
}
