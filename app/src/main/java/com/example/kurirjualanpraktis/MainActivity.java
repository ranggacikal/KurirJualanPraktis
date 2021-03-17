package com.example.kurirjualanpraktis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kurirjualanpraktis.sharedPreferences.Pref;
import com.example.kurirjualanpraktis.sharedPreferences.SharedPrefManager;
import com.example.kurirjualanpraktis.sharedPreferences.loginuser;
import com.facebook.login.LoginManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imgUser, imgNotif;
    TextView txtNamaUser;
    CardView cardPenjemputan, cardPengantaran, cardPenjemputanRetur, cardPengantaranRetur;
    LinearLayout linearClickAkun;

    loginuser user;

    Pref pref;

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
        linearClickAkun = findViewById(R.id.linear_click_akun);

        pref = new Pref(MainActivity.this.getApplicationContext());

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

        cardPenjemputanRetur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PenjemputanReturActivity.class));
            }
        });

        cardPengantaranRetur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PengantaranReturActivity.class));
            }
        });

        linearClickAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampilDialog();
            }
        });


    }

    private void tampilDialog() {

        Dialog alertDialog = new Dialog(this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_akun);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        CircleImageView imgAkun = alertDialog.findViewById(R.id.img_akun_dialog);
        TextView txtNama = alertDialog.findViewById(R.id.text_nama_dialog);
        RelativeLayout relativeLogout = alertDialog.findViewById(R.id.relative_logout);

        String urlFotoUser = "https://jualanpraktis.net/file-kurir/foto/";

        Glide.with(MainActivity.this)
                .load(urlFotoUser+user.getFoto())
                .error(R.drawable.icon_app)
                .into(imgAkun);

        txtNama.setText(user.getNama());

        relativeLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keluarAkun();
            }
        });

    }

    private void keluarAkun() {

        if (SharedPrefManager.getInstance(MainActivity.this).isLoggedIn()) {
            SharedPrefManager.getInstance(MainActivity.this).logout();
            pref.setLoginMethod("");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
//                    getActivity().finish();
//                    getActivity().overridePendingTransition(0, 0);
//                    getActivity().startActivity(getActivity().getIntent().setFlags(getActivity().getIntent().FLAG_ACTIVITY_NO_ANIMATION));
//                    getActivity().overridePendingTransition(0, 0);
            Toast.makeText(MainActivity.this, "Anda Telah Logout", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(MainActivity.this, "Anda Belum Login", Toast.LENGTH_SHORT).show();
        }

    }
}