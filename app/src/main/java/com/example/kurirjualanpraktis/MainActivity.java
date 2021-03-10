package com.example.kurirjualanpraktis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kurirjualanpraktis.sharedPreferences.SharedPrefManager;
import com.example.kurirjualanpraktis.sharedPreferences.loginuser;

public class MainActivity extends AppCompatActivity {

    ImageView imgUser, imgNotif;
    TextView txtNamaUser;
    CardView cardPenjemputan, cardPengantaran, cardPenjemputanRetur, cardPengantaranRetur;

    loginuser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = SharedPrefManager.getInstance(MainActivity.this).getUser();

        imgUser = findViewById(R.id.img_user_main);
        imgNotif = findViewById(R.id.img_notifikasi);
        txtNamaUser = findViewById(R.id.tv_nama_user_main);
        cardPenjemputan = findViewById(R.id.card_penjemputan_produk);
        cardPengantaran = findViewById(R.id.card_pengantaran_produk);
        cardPenjemputanRetur = findViewById(R.id.card_penjemputan_produkl_retur);
        cardPengantaranRetur = findViewById(R.id.card_pengantaran_produk_retur);

        txtNamaUser.setText(user.getNama());

        String urlFoto = "https://jualanpraktis.net/file-kurir/foto/";

        Glide.with(MainActivity.this)
                .load(urlFoto+user.getFoto())
                .error(R.drawable.icon_app)
                .into(imgUser);

        cardPenjemputan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PenjemputanActivity.class));
            }
        });

        cardPengantaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PengantaranActivity.class));
            }
        });


    }
}