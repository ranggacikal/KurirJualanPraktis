package com.example.kurirjualanpraktis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.kurirjualanpraktis.adapter.PenjemputanAdapter;
import com.example.kurirjualanpraktis.adapter.PenjemputanReturAdapter;
import com.example.kurirjualanpraktis.sharedPreferences.SharedPrefManager;
import com.example.kurirjualanpraktis.sharedPreferences.loginuser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class PenjemputanReturActivity extends AppCompatActivity {

    ImageView imgBack;
    TextView txtKosong;
    RecyclerView recyclerPenjemputanRetur;

    loginuser user;

    ArrayList<HashMap<String, String>> dataPenjemputanRetur = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjemputan_retur);

        user = SharedPrefManager.getInstance(PenjemputanReturActivity.this).getUser();
        AndroidNetworking.initialize(PenjemputanReturActivity.this.getApplicationContext());

        imgBack = findViewById(R.id.img_back_penjemputan_retur);
        txtKosong = findViewById(R.id.text_kosong_penjemputan_retur);
        recyclerPenjemputanRetur = findViewById(R.id.recycler_penjemputan_retur);

        recyclerPenjemputanRetur.setHasFixedSize(true);
        recyclerPenjemputanRetur.setLayoutManager(new LinearLayoutManager(PenjemputanReturActivity.this));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadPenjemputanRetur();
    }

    private void loadPenjemputanRetur() {

        ProgressDialog progressDialog = new ProgressDialog(PenjemputanReturActivity.this);
        progressDialog.setTitle("Memuat Data");
        progressDialog.setMessage("Loading");
        progressDialog.show();

        String url = "https://jualanpraktis.net/android/kurir/list-penjemputan-retur.php";

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.post(url)
                .addBodyParameter("id", user.getId())
                .setTag(PenjemputanReturActivity.this)
                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressDialog.dismiss();

                        dataPenjemputanRetur.clear();
                        try {
                            JSONArray array = response.getJSONArray("data");
                            for (int i = 0;i<array.length();i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                HashMap<String,String> data = new HashMap<>();
                                data.put("id_pengiriman",jsonObject.getString("id_pengiriman"));
                                data.put("id_costomer",jsonObject.getString("id_customer"));
                                data.put("id_member",jsonObject.getString("id_member"));
                                data.put("nama_penerima",jsonObject.getString("nama_penerima"));
                                data.put("id_transaksi",jsonObject.getString("id_transaksi"));
                                data.put("provinsi",jsonObject.getString("idProvinsi"));
                                data.put("kota",jsonObject.getString("idKota"));
                                data.put("kecamatan",jsonObject.getString("idKecamatan"));
                                data.put("alamat",jsonObject.getString("alamat"));
                                data.put("no_hp",jsonObject.getString("no_hp"));
                                dataPenjemputanRetur.add(data);
                            }

                            PenjemputanReturAdapter adapter = new PenjemputanReturAdapter(PenjemputanReturActivity.this, dataPenjemputanRetur);
                            recyclerPenjemputanRetur.setAdapter(adapter);

                            if (dataPenjemputanRetur.isEmpty()){
                                recyclerPenjemputanRetur.setVisibility(View.GONE);
                                txtKosong.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        dataPenjemputanRetur.clear();

                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail

                            // get parsed error object (If ApiError is your class)
                            Toast.makeText(PenjemputanReturActivity.this, "Gagal mendapatkan data.", Toast.LENGTH_SHORT).show();
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            if (anError.getErrorDetail().equals("connectionError")){
                                Toast.makeText(PenjemputanReturActivity.this, "Tidak ada koneksi internet.", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(PenjemputanReturActivity.this, "Gagal mendapatkan data.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPenjemputanRetur();
    }
}