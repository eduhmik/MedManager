package com.example.eduh_mik.med_manager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.adapters.viewholders.MedicineViewHolder;
import com.example.eduh_mik.med_manager.models.Medicine;


import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineViewHolder> implements Filterable{

    private Context _context;
    private ArrayList<Medicine> medicineList,finalMedicineList;
    public MedicineAdapter(Context mContext, ArrayList<Medicine> medicineItemList) {
        this._context = mContext;
        this.medicineList = medicineItemList;
        this.finalMedicineList = medicineItemList;
    }
    @Override
    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_medicine_item, parent, false);
        return new MedicineViewHolder(_context,itemView);
    }

    @Override
    public void onBindViewHolder(final MedicineViewHolder holder, int position) {
        holder.bind(medicineList.get(position));
    }
    @Override
    public int getItemCount() {
        return null== medicineList ? 0 : medicineList.size();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                medicineList = (ArrayList<Medicine>) results.values;
                MedicineAdapter.this.notifyDataSetChanged();
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Medicine> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = finalMedicineList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString());
                }
                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }
    protected ArrayList<Medicine> getFilteredResults(String constraint) {
        ArrayList<Medicine> results = new ArrayList<>();
        for (Medicine item : finalMedicineList) {
            if (item.getName().toString().toLowerCase().contains(constraint.toLowerCase()))
            {
                results.add(item);
            }
        }
        return results;
    }
}
