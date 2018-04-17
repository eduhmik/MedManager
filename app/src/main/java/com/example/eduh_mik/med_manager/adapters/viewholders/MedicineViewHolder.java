package com.example.eduh_mik.med_manager.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.models.Medicine;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Period;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mayore on 10/28/2017.
 */

public class MedicineViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_Name) TextView tvName;
    @BindView(R.id.tv_Description)
    TextView tvDescription;
    @BindView(R.id.tv_startDate)
    TextView tvStartDate;
    @BindView(R.id.tv_endDate)
    TextView tvEndDate;
    @BindView(R.id.tv_dosesTaken)
    TextView tvDosesTaken;
    @BindView(R.id.tv_percentagTaken)
    TextView tvPercentagTaken;
    @BindView(R.id.tv_dosesLeft)
    TextView tvDosesLeft;
    @BindView(R.id.tv_percentagLeft)
    TextView tvPercentagLeft;
    @BindView(R.id.iv_historyDriverImage)
    ImageView ivHistoryDriverImage;
    private Context _context;

    public MedicineViewHolder(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Medicine medicine) {
        tvName.setText(medicine.getName());
        tvDescription.setText(medicine.getDescription());
        tvStartDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(medicine.getStartDate()));
        tvEndDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(medicine.getEndDate()));

        Calendar calendarPast = Calendar.getInstance();
        Calendar calendarRemaining = Calendar.getInstance();
        calendarPast.setTime(medicine.getStartDate());
        calendarRemaining.setTime(medicine.getEndDate());
        DateTime dtPast = new DateTime(calendarPast);
        DateTime dtRemaining = new DateTime(calendarRemaining);
        DateTime dtPresent = new DateTime(Calendar.getInstance());
        Hours hoursPast = Hours.hoursBetween(dtPast,dtPresent);
        Hours hoursRemaining = Hours.hoursBetween(dtPresent,dtRemaining);
        Hours hoursTotal = Hours.hoursBetween(dtPast,dtRemaining);
        if(calendarPast.after(Calendar.getInstance())){
            tvDosesTaken.setText(String.valueOf(0));
            tvDosesTaken.setText(String.valueOf(hoursTotal.getHours()*medicine.getFrequency()/24));
            tvPercentagTaken.setText(0+"%");
            tvPercentagLeft.setText(100+"%");
        }else{
            tvDosesTaken.setText(String.valueOf((hoursPast.getHours()*medicine.getFrequency()/24)+1));
            tvDosesLeft.setText(String.valueOf(hoursRemaining.getHours()*medicine.getFrequency()/24));
            int intHoursPast = hoursPast.getHours();
            int intHoursRemaining = hoursRemaining.getHours();
            int intHoursTotal = hoursTotal.getHours();
            int percentagePast = (intHoursPast*100)/intHoursTotal;
            int percentageRemaining = 100-percentagePast;

            tvPercentagTaken.setText(percentagePast+"%");
            tvPercentagLeft.setText(percentageRemaining+"%");
        }

    }
    @OnClick(R.id.card_medicine)
    public void onCardClicked() {

    }
}