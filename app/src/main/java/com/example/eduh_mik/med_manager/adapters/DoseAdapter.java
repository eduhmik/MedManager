package com.example.eduh_mik.med_manager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.adapters.viewholders.DoseViewHolder;
import com.example.eduh_mik.med_manager.adapters.viewholders.MedicineViewHolder;
import com.example.eduh_mik.med_manager.models.Dose;
import com.example.eduh_mik.med_manager.models.Medicine;

import java.util.ArrayList;

public class DoseAdapter extends RecyclerView.Adapter<DoseViewHolder>{

    private Context _context;
    private ArrayList<Dose> doseList;
    public DoseAdapter(Context mContext, ArrayList<Dose> doseItemList) {
        this._context = mContext;
        this.doseList = doseItemList;
    }
    @Override
    public DoseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_intake_item, parent, false);
        return new DoseViewHolder(_context,itemView);
    }

    @Override
    public void onBindViewHolder(final DoseViewHolder holder, int position) {
        holder.bind(doseList.get(position));
    }
    @Override
    public int getItemCount() {
        return null== doseList ? 0 : doseList.size();
    }
}
