package com.example.nytravel;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;



public class DatePickerFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),dateSetListner,year,month,day);
    }

    private DatePickerDialog.OnDateSetListener dateSetListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            String tombol = ((MainActivity)getActivity()).button_tanggal();
            if(tombol.equals("berangkat")){
                ((MainActivity)getActivity()).setTanggalBerangkat(view.getDayOfMonth()+"/"+view.getMonth()+"/"+view.getYear());
            }else{
                ((MainActivity)getActivity()).setTanggalPulang(view.getDayOfMonth()+"/"+view.getMonth()+"/"+view.getYear());
            }
        }
    };
}