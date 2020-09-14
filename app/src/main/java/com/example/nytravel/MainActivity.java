package com.example.nytravel;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.text.InputType;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private static final int TEXT_REQUEST = 1;

    private int saldo_akhir = 0;
    public String tombol;
    private boolean pulang_pergi_check = false;
    private int jumlah_tiket = 0;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Spinner
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.destination_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Switch
        final Switch pulangpergi = findViewById(R.id.switch2);
        final Button date = findViewById(R.id.input_date1);
        final Button time = findViewById(R.id.input_time1);

        date.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);

        pulangpergi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    date.setVisibility(View.VISIBLE);
                    time.setVisibility(View.VISIBLE);
                    pulang_pergi_check = true;
                } else {
                    date.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.INVISIBLE);
                    pulang_pergi_check = false;
                }
            }
        });
    }


    //Top Up Saldo
    public void tambahsaldo(View view) {
        openDialog();
    }

    private void openDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        TextView title = new TextView(this);

        title.setText("MASUKAN JUMLAH SALDO");
        title.setPadding(10, 30, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        title.setTypeface(null, Typeface.BOLD);
        alertDialog.setCustomTitle(title);

        final EditText saldo = new EditText(this);
        saldo.setGravity(Gravity.CENTER_HORIZONTAL);
        saldo.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialog.setView(saldo, 50, 0, 50, 0);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "TAMBAH SALDO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                TextView coba = (TextView) findViewById(R.id.saldo);
                int saldo_sebelum = Integer.parseInt(coba.getText().toString());
                saldo_akhir = saldo_sebelum + Integer.parseInt(saldo.getText().toString());
                coba.setText(String.valueOf(saldo_akhir));
                Toast.makeText(MainActivity.this, "TOP UP SUKSES", Toast.LENGTH_SHORT).show();

            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "TOP UP DIBATALKAN", Toast.LENGTH_SHORT).show();
            }
        });

        new Dialog(getApplicationContext());
        alertDialog.show();
    }

    //DatePicker

    public void InputDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
        this.setTombol("berangkat");
    }

    public void InputDate1(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
        this.setTombol("pulang");
    }

    private void setTombol(String tombol) {
        this.tombol = tombol;
    }

    public String button_tanggal() {
        return tombol;
    }

    public void setTanggalBerangkat(String tanggal) {
        Button button_tanggal = findViewById(R.id.input_date);
        button_tanggal.setText(tanggal);
    }

    public void setTanggalPulang(String tanggal) {
        Button button_tanggal = findViewById(R.id.input_date1);
        button_tanggal.setText(tanggal);
    }


    //Time Picker

    public void setTime(View view) {
        calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Button btn_time = (Button) findViewById(R.id.input_time);

                btn_time.setText(String.format("%02d", hourOfDay)+":"+String.format("%02d", minute));

            }
        },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this));

        timePickerDialog.show();
    }

    public void setTime2(View view) {
        calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Button btn_time2 = (Button) findViewById(R.id.input_time1);

                btn_time2.setText(String.format("%02d", hourOfDay)+":"+String.format("%02d", minute));
            }
        },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this));

        timePickerDialog.show();
    }



    // Semua pesanan
    public void check(View view) {

        Spinner tujuan = findViewById(R.id.spinner);
        Button keberangkatan = findViewById(R.id.input_date);
        Button pulang = findViewById(R.id.input_date1);
        EditText jumlah_tiket = findViewById(R.id.count);
        Button jam_keberangkatan = findViewById(R.id.input_time);
        Button jam_pulang = findViewById(R.id.input_time1);


        boolean saldo_check = false;
        boolean keberangkatan_check = false;
        boolean pulang_check = false;

        //Jumlah Tiket

        if (TextUtils.isEmpty(jumlah_tiket.getText().toString())) {
            jumlah_tiket.setError("Anda belum mengisi jumlah tiket");
            return;
        }

        //list harga dan tujuan

        int harga = 0;
        String tujuan_keberangkatan = tujuan.getSelectedItem().toString();
        if (tujuan_keberangkatan.equals("Bandung Rp 100.000")) {
            harga = 100000;
        } else if (tujuan_keberangkatan.equals("Jakarta Rp 100.000")) {
            harga = 100000;
        } else if (tujuan_keberangkatan.equals("Yogyakarta Rp 300.000")) {
            harga = 300000;
        } else if (tujuan_keberangkatan.equals("Bali Rp 500.000")) {
            harga = 500000;
        } else if (tujuan_keberangkatan.equals("Palembang Rp 200.000")) {
            harga = 200000;
        }

        //jika pulang pergi harganya dobel

        if (pulang_pergi_check != false) {
            harga = harga * 2;
        }

        //Total tiket

        int total_tiket = Integer.parseInt(jumlah_tiket.getText().toString());
        int total_akhir = harga * total_tiket;

        //Cek Saldo

        if (saldo_akhir < total_akhir) {
            Toast.makeText(MainActivity.this, "MAAF, SALDO TIDAK MENCUKUPI", Toast.LENGTH_SHORT).show();
        } else {
            saldo_check = true;
        }

        //check date
        if (keberangkatan.getText().toString().equals("Tanggal")) {
            Toast.makeText(MainActivity.this, "MAAF, ANDA BELUM MEMILIH TANGGAL KEBERANGKATAN", Toast.LENGTH_SHORT).show();
        } else {
            keberangkatan_check = true;
        }

        if (pulang_pergi_check != false) {
            if (pulang.getText().toString().equals("Tanggal")) {
                Toast.makeText(MainActivity.this, "MAAF, ANDA BELUM MEMILIH TANGGAL PULANG", Toast.LENGTH_SHORT).show();
            } else {
                pulang_check = true;
            }
        }

        Intent newtap = new Intent(this, CheckOutActivity.class);

        newtap.putExtra("tujuan", tujuan_keberangkatan.replaceAll(" .+$", "").toUpperCase());
        newtap.putExtra("harga_tiket", String.valueOf(harga));
        newtap.putExtra("jumlah_tiket", String.valueOf(total_tiket));
        newtap.putExtra("harga_total", String.valueOf(total_akhir));
        newtap.putExtra("pulang_pergi", pulang_pergi_check);
        newtap.putExtra("saldo", String.valueOf(saldo_akhir));

        if (pulang_pergi_check != false) {
            if (saldo_check == true && keberangkatan_check == true && pulang_check == true) {
                Log.d("keluaran", "TERPENUHI");

                newtap.putExtra("tanggal_berangkat", keberangkatan.getText().toString());
                newtap.putExtra("jam_berangkat",jam_keberangkatan.getText().toString());
                newtap.putExtra("tanggal_pulang", pulang.getText().toString());
                newtap.putExtra("jam_pulang",jam_pulang.getText().toString());
                startActivityForResult(newtap, TEXT_REQUEST);

            } else {
                Log.d("keluaran", "BELUM");
            }
        } else {
            if (saldo_check == true && keberangkatan_check == true) {
                Log.d("keluaran", "TERPENUHI");

                newtap.putExtra("tanggal_berangkat", keberangkatan.getText().toString());
                newtap.putExtra("jam_berangkat",jam_keberangkatan.getText().toString());
                startActivityForResult(newtap, TEXT_REQUEST);
            } else {
                Log.d("keluaran", "BELUM");
            }
        }
    }
}
   /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                TextView terimakasih = (TextView) findViewById(R.id.textView8);
                TextView saldonya = (TextView) findViewById(R.id.textView3);

                terimakasih.setVisibility(View.VISIBLE);

                String harga_total = data.getStringExtra(CheckOutActivity.EXTRA_REPLY);
                Log.d("keluarnya", harga_total);
                saldo_akhir = Integer.parseInt(harga_total);
                saldonya.setText(harga_total.toString());
            }
        }
    }
}*/
