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
import com.example.kurirjualanpraktis.adapter.PengantaranAdapter;
import com.example.kurirjualanpraktis.adapter.PengantaranReturAdapter;
import com.example.kurirjualanpraktis.sharedPreferences.SharedPrefManager;
import com.example.kurirjualanpraktis.sharedPreferences.loginuser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class PengantaranReturActivity extends AppCompatActivity {

    ImageView imgBack;
    TextView txtKosong;
    RecyclerView recyclerPengantaranRetur;

    loginuser user;

    ArrayList<HashMap<String, String>> dataPengantaranRetur = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengantaran_retur);

        user = SharedPrefManager.getInstance(PengantaranReturActivity.this).getUser();
        AndroidNetworking.initialize(PengantaranReturActivity.this.getApplicationContext());

        imgBack = findViewById(R.id.img_back_pengantaran_retur);
        txtKosong = findViewById(R.id.text_kosong_pengantaran_retur);
        recyclerPengantaranRetur = findViewById(R.id.recycler_pengantaran_retur);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerPengantaranRetur.setHasFixedSize(true);
        recyclerPengantaranRetur.setLayoutManager(new LinearLayoutManager(PengantaranReturActivity.this));

        loadPengantaranRetur();
    }

    private void loadPengantaranRetur() {

        ProgressDialog progressDialog = new ProgressDialog(PengantaranReturActivity.this);
        progressDialog.setTitle("Memuat Data");
        progressDialog.setMessage("Loading");
        progressDialog.show();

        String url = "https://jualanpraktis.net/android/kurir/list-pengantaran-retur.php";

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.post(url)
                .addBodyParameter("id", user.getId())
                .setTag(PengantaranReturActivity.this)
                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressDialog.dismiss();

                        dataPengantaranRetur.clear();
                        try {
                            JSONArray array = response.getJSONArray("data");
                            for (int i = 0;i<array.length();i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                HashMap<String,String> data = new HashMap<>();
                                data.put("id_pengiriman",jsonObject.getString("id_pengiriman"));
                                data.put("nama_toko",jsonObject.getString("nama_company"));
                                data.put("provinsi",jsonObject.getString("province"));
                                data.put("kota",jsonObject.getString("city_name"));
                                data.put("kecamatan",jsonObject.getString("subdistrict_name"));
                                data.put("kelurahan",jsonObject.getString("kelurahan"));
                                data.put("alamat",jsonObject.getString("alamat"));
                                data.put("no_hp",jsonObject.getString("no_hp"));
                                data.put("nama_penerima",jsonObject.getString("nama_penerima"));
                                data.put("id_transaksi",jsonObject.getString("id_transaksi"));
                                dataPengantaranRetur.add(data);
                            }

                            PengantaranReturAdapter adapter = new PengantaranReturAdapter(PengantaranReturActivity.this, dataPengantaranRetur);
                            recyclerPengantaranRetur.setAdapter(adapter);

                            if (dataPengantaranRetur.isEmpty()){
                                recyclerPengantaranRetur.setVisibility(View.GONE);
                                txtKosong.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        dataPengantaranRetur.clear();

                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail

                            // get parsed error object (If ApiError is your class)
                            Toast.makeText(PengantaranReturActivity.this, "Gagal mendapatkan data.", Toast.LENGTH_SHORT).show();
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            if (anError.getErrorDetail().equals("connectionError")){
                                Toast.makeText(PengantaranReturActivity.this, "Tidak ada koneksi internet.", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(PengantaranReturActivity.this, "Gagal mendapatkan data.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPengantaranRetur();
    }
}