package com.example.eduh_mik.med_manager.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.activities.MainActivity;
import com.example.eduh_mik.med_manager.adapters.MedicineAdapter;
import com.example.eduh_mik.med_manager.database.AppDatabase;
import com.example.eduh_mik.med_manager.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.med_manager.models.Medicine;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MedicineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicineFragment extends Fragment implements MainActivity.OnMedicineSearchListener{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private MedicineAdapter medicineAdapter;
    private ArrayList<Medicine> medicineItems = new ArrayList<>();

    public MedicineFragment() {
        // Required empty public constructor
    }

    public static MedicineFragment newInstance() {
        return new MedicineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medicine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMedicineItems();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        medicineAdapter = new MedicineAdapter(getActivity(), medicineItems);
        recyclerView.setAdapter(medicineAdapter);
        loadMedicineItems();
    }
    public void loadMedicineItems(){
        medicineItems.clear();
        medicineItems.addAll(AppDatabase.getAppDatabase(getActivity()).medicineDao().getAll());
        medicineAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            ((MainActivity)getActivity()).initOnMedicineSearchListener(this);
        } else {
            throw new RuntimeException(context.toString()
                    + " must be MAinActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onMedicineSearched(String name) {
        medicineAdapter.getFilter().filter(name);
    }
}
