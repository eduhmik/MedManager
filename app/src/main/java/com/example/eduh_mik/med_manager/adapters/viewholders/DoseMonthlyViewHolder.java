package com.example.eduh_mik.med_manager.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.models.Dose;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mayore on 10/28/2017.
 */

public class DoseMonthlyViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_audioAlbumArt)
    ImageView ivAudioAlbumArt;
    @BindView(R.id.tv_medicineName)
    TextView tvMedicineName;
    @BindView(R.id.tv_DateTime)
    TextView tvDateTime;
    private Context _context;

    public DoseMonthlyViewHolder(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Dose dose) {
        tvMedicineName.setText(dose.getMedicine().getName());
        tvDateTime.setText(dose.getDoseTime() + " · " + dose.getDoseDate());
        Glide.with(_context).load(R.drawable.png_logo).into(ivAudioAlbumArt);
    }
}