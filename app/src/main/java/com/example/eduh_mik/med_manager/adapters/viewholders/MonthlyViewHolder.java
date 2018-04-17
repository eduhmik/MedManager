package com.example.eduh_mik.med_manager.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.adapters.DoseMonthlyAdapter;
import com.example.eduh_mik.med_manager.adapters.MonthlyAdapter;
import com.example.eduh_mik.med_manager.models.Monthly;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mayore on 10/28/2017.
 */

public class MonthlyViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.itemTitle)
    TextView itemTitle;
    @BindView(R.id.btnMore)
    Button btnMore;
    @BindView(R.id.recycler_view_list)
    RecyclerView recyclerViewList;
    private Context _context;
    private LinearLayoutManager linearLayoutManager;

    public MonthlyViewHolder(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }
    public void bind(Monthly monthly) {
        this.itemTitle.setText(monthly.getName());
        this.linearLayoutManager = new LinearLayoutManager(_context,LinearLayoutManager.HORIZONTAL,false);
        this.recyclerViewList.setLayoutManager(linearLayoutManager);
        this.recyclerViewList.setAdapter(new DoseMonthlyAdapter(_context,monthly.getDoses()));
    }
    @OnClick(R.id.btnMore)
    public void onClickMore(){
        linearLayoutManager.scrollToPosition(recyclerViewList.getAdapter().getItemCount()-1);
    }
}