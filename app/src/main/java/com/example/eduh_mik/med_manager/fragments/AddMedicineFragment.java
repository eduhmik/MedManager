package com.example.eduh_mik.med_manager.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.eduh_mik.med_manager.R;
import com.example.eduh_mik.med_manager.activities.AddMedicineActivity;
import com.example.eduh_mik.med_manager.base.BaseFragment;
import com.example.eduh_mik.med_manager.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.med_manager.models.Medicine;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddMedicineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMedicineFragment extends BaseFragment {
    public Calendar calendarStartDate, calendarEndDate;
    public boolean startDateSet, endDateSet,startTimeSet, endTimeSet;
    public int interval = 1;
    @BindView(R.id.et_Name)
    EditText etName;
    @BindView(R.id.et_Description)
    EditText etDescription;
    @BindView(R.id.et_Ailment)
    EditText etAilment;
    @BindView(R.id.tv_StartDate)
    TextView tvStartDate;
    @BindView(R.id.tv_EndDate)
    TextView tvEndDate;
    @BindView(R.id.rg_frequency)
    RadioGroup rgFrequency;
    Unbinder unbinder;
    @BindView(R.id.tv_StartTime)
    TextView tvStartTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;

    @OnCheckedChanged({R.id.rb_once, R.id.rb_twice, R.id.rb_thrice, R.id.rb_four_times})
    public void selectInterval(CompoundButton button, boolean checked) {
        switch (button.getId()) {
            case R.id.rb_once:
                if (checked) interval = 1;
                break;
            case R.id.rb_twice:
                if (checked) interval = 2;
                break;
            case R.id.rb_thrice:
                if (checked) interval = 3;
                break;
            case R.id.rb_four_times:
                if (checked) interval = 4;
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.btn_Submit)
    public void onViewClicked() {
        validate();
    }

    @OnClick(R.id.lin_StartDate)
    public void onLinStartDateClicked() {
        pickDate(1);
    }

    @OnClick(R.id.lin_EndDate)
    public void onLinEndDateClicked() {
        pickDate(2);
    }
    @OnClick({R.id.lin_StartTime, R.id.lin_endTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_StartTime:
                pickTime(1);
                break;
            case R.id.lin_endTime:
                pickTime(2);
                break;
        }
    }
    public void validate() {
        String name = etName.getText().toString();
        String description = etDescription.getText().toString();
        String ailment = etAilment.getText().toString();
        if (TextUtils.isEmpty(name)) {
            etName.requestFocus();
            etName.setError("Enter Medicine Name");
        } else if (TextUtils.isEmpty(description)) {
            etDescription.requestFocus();
            etDescription.setError("Enter Medicine Description");
        } else if (TextUtils.isEmpty(ailment)) {
            etAilment.requestFocus();
            etAilment.setError("Enter Ailment");
        } else if (!startDateSet) {
            tvStartDate.requestFocus();
            showToast("Set Medication Start Date");
        }else if (!startTimeSet) {
            tvStartTime.requestFocus();
            showToast("Set Medication Start Time");
        } else if (!endDateSet) {
            tvEndDate.requestFocus();
            showToast("Set Medication End Date");
        } else if (!endTimeSet) {
            tvEndTime.requestFocus();
            showToast("Set Medication End Time");
        } else if (calendarStartDate.after(calendarEndDate)) {
            tvEndDate.requestFocus();
            showToast("End Date Must Come After Start Date");
        } else {
            if (getActivity() instanceof AddMedicineActivity) {
                ((AddMedicineActivity) getActivity()).replaceFragment(ConfirmMedicineFragment.newInstance(new Medicine(name, description, ailment, interval, calendarStartDate.getTime(), calendarEndDate.getTime())), "Confirm Medicine Details");
            } else {
                showToast("Invalid Activity");
            }
        }
    }
    public void pickTime(int i){
        if (i==1){
            Calendar mcurrentTime = Calendar.getInstance();
            final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            final int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    startTimeSet = true;
                    String time =  selectedHour + ":" + selectedMinute;
                    tvStartTime.setText(time);
                    calendarStartDate.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendarStartDate.set(Calendar.MINUTE, selectedMinute);
                    calendarStartDate.set(Calendar.SECOND, 0);
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }else {
            Calendar mcurrentTime = Calendar.getInstance();
            final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            final int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    endTimeSet = true;
                    String time =  selectedHour + ":" + selectedMinute;
                    tvEndTime.setText(time);
                    calendarEndDate.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendarEndDate.set(Calendar.MINUTE, selectedMinute);
                    calendarEndDate.set(Calendar.SECOND, 0);
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
    }
    public void pickDate(int i) {
        if (i == 1) {
            int mYear, mMonth, mDay;
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            startDateSet = true;
                            calendarStartDate = Calendar.getInstance();
                            calendarStartDate.set(Calendar.YEAR, year);
                            calendarStartDate.set(Calendar.MONTH, monthOfYear);
                            calendarStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String startDate = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
                            tvStartDate.setText(startDate);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else {
            int mYear, mMonth, mDay;
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            endDateSet = true;
                            calendarEndDate = Calendar.getInstance();
                            calendarEndDate.set(Calendar.YEAR, year);
                            calendarEndDate.set(Calendar.MONTH, monthOfYear);
                            calendarEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String endDate = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
                            tvEndDate.setText(endDate);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    private OnFragmentInteractionListener mListener;

    public AddMedicineFragment() {
        // Required empty public constructor
    }

    public static AddMedicineFragment newInstance() {
        return new AddMedicineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_medicine, container, false);
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
    }
}
