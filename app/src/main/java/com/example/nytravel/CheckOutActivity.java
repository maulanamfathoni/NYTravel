package com.example.nytravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CheckOutActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
            "com.example.android.nytravel.extra.REPLY";
    private static final String LOG_TAG = CheckOutActivity.class.getName();
    String hargasemuanya;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        TextView tujuan = findViewById(R.id.tujuan);
        TextView tanggalkeberangkatan = findViewById(R.id.tanggalk);
        TextView tanggalpulang = (TextView) findViewById(R.id.tanggalp);
        TextView jumlahtiket = (TextView) findViewById(R.id.jumlahtiket);
        TextView totalharga = (TextView) findViewById(R.id.outputttl);

        Intent ambil = getIntent();
        tujuan.setText(ambil.getStringExtra("tujuan"));
        tanggalkeberangkatan.setText(ambil.getStringExtra("tanggal_berangkat") + " " + ambil.getStringExtra("jam_berangkat"));
        tanggalpulang.setText(ambil.getStringExtra("tanggal_pulang"));
        jumlahtiket.setText(ambil.getStringExtra("jumlah_tiket"));
        totalharga.setText(ambil.getStringExtra("harga_total"));


        hargasemuanya = ambil.getStringExtra("HargaAkhir");
    }

    public void returnReplay(View view) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY,String.valueOf(hargasemuanya));
        setResult(RESULT_OK, replyIntent);
        Log.d(LOG_TAG, "End");
        finish();
    }
}
