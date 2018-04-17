package com.example.eduh_mik.med_manager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.adapters.viewholders.MonthlyViewHolder;
import com.example.eduh_mik.med_manager.models.Dose;
import com.example.eduh_mik.med_manager.models.Monthly;

import java.util.ArrayList;

public class MonthlyAdapter extends RecyclerView.Adapter<MonthlyViewHolder>{

    private Context _context;
    private ArrayList<Monthly> monthlyList;
    public MonthlyAdapter(Context mContext, ArrayList<Monthly> monthlyItemList) {
        this._context = mContext;
        this.monthlyList = monthlyItemList;
    }
    @Override
    public MonthlyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_monthly_section, parent, false);
        return new MonthlyViewHolder(_context,itemView);
    }

    @Override
    public void onBindViewHolder(final MonthlyViewHolder holder, int position) {
        holder.bind(monthlyList.get(position));
    }
    @Override
    public int getItemCount() {
        return null== monthlyList ? 0 : monthlyList.size();
    }
}
