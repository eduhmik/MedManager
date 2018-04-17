package com.example.eduh_mik.med_manager.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.models.Dose;
import com.example.eduh_mik.med_manager.models.Medicine;

import org.joda.time.DateTime;
import org.joda.time.Hours;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mayore on 10/28/2017.
 */

public class DoseViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_Name)
    TextView tvName;
    @BindView(R.id.tv_Description)
    TextView tvDescription;
    @BindView(R.id.iv_not_taken)
    ImageView ivNotTaken;
    @BindView(R.id.iv_taken)
    ImageView ivTaken;
    private Context _context;

    public DoseViewHolder(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Dose dose) {
        tvName.setText(dose.getMedicine().getName());
        tvDescription.setText(dose.getDoseTime()+" · "+dose.getDoseDate()+" · "+dose.getDoseWaitTime());
        ivNotTaken.setVisibility(dose.getDoseIsTaked() ? View.GONE : View.VISIBLE);
        ivTaken.setVisibility(dose.getDoseIsTaked() ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.card_medicine)
    public void onCardClicked() {

    }
}