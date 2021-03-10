package com.example.kurirjualanpraktis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.kurirjualanpraktis.adapter.DetailProdukAdapter;
import com.example.kurirjualanpraktis.adapter.DetailVendorAdapter;
import com.example.kurirjualanpraktis.sharedPreferences.SharedPrefManager;
import com.example.kurirjualanpraktis.sharedPreferences.loginuser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class DetailPenjemputanActivity extends AppCompatActivity {

    loginuser user;

    ArrayList<HashMap<String, String>> dataDetailPenjemputan = new ArrayList<>();

    RecyclerView recyclerVendor, recyclerProduk;
    Button btnUpdate;
    ImageView imgBack;

    DetailPenjemputanActivity detailPenjemputanActivity;

    public String id_transaksi;
    public String id_member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penjemputan);

        user = SharedPrefManager.getInstance(DetailPenjemputanActivity.this).getUser();
        AndroidNetworking.initialize(DetailPenjemputanActivity.this.getApplicationContext());

        recyclerVendor = findViewById(R.id.recycler_detail_vendor);
        recyclerProduk = findViewById(R.id.recycler_detail_produk);
        btnUpdate = findViewById(R.id.btn_update_penjemputan);
        imgBack = findViewById(R.id.img_back_detail_penjemputan);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePenjemputan();
            }
        });

        loadDataDetailPenjemputan();


    }

    private void updatePenjemputan() {

        String host = "https://jualanpraktis.net/android/kurir/update-penjemputan.php";

        ProgressDialog progressDialog = new ProgressDialog(DetailPenjemputanActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Update Status Penjemputan");
        progressDialog.setCancelable(false);
        progressDialog.show();


        Map<String, String> params = new HashMap<String, String>();
        params.put("id_pengiriman", getIntent().getStringExtra("id_pengiriman"));
        params.put("id_transaksi", id_transaksi);
        params.put("id_member", id_member);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        AndroidNetworking.post(host)
                .addBodyParameter(params)
                .setTag(DetailPenjemputanActivity.this)
                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Toast.makeText(DetailPenjemputanActivity.this, "Berhasil Update Status Penjemputan", Toast.LENGTH_SHORT).show();
                        finish();


                        try {
                            Toast.makeText(getApplicationContext(), response.getString("response"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Gagal Update Status Penjemputan", Toast.LENGTH_SHORT).show();
                    }

                });

    }

    private void loadDataDetailPenjemputan() {

        ProgressDialog progressDialog = new ProgressDialog(DetailPenjemputanActivity.this);
        progressDialog.setTitle("Memuat Data");
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        String url = "https://jualanpraktis.net/android/kurir/detail-penjemputan.php";

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.post(url)
                .addBodyParameter("id_pengiriman", getIntent().getStringExtra("id_pengiriman"))
                .setTag(DetailPenjemputanActivity.this)
                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressDialog.dismiss();
                        dataDetailPenjemputan.clear();
                        try {
                            JSONArray array = response.getJSONArray("data");
                            for (int i = 0;i<array.length();i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                HashMap<String,String> data = new HashMap<>();
                                data.put("id_pengiriman",jsonObject.getString("id_pengiriman"));
                                data.put("id_customer",jsonObject.getString("id_customer"));
                                data.put("id_member",jsonObject.getString("id_member"));
                                data.put("id_transaksi",jsonObject.getString("id_transaksi"));
                                data.put("nama_toko",jsonObject.getString("nama_company"));
                                data.put("provinsi",jsonObject.getString("province"));
                                data.put("kota",jsonObject.getString("city_name"));
                                data.put("kecamatan",jsonObject.getString("subdistrict_name"));
                                data.put("kelurahan",jsonObject.getString("kelurahan"));
                                data.put("alamat",jsonObject.getString("alamat"));
                                data.put("no_hp",jsonObject.getString("no_hp"));
                                data.put("nama_pemilik",jsonObject.getString("nama"));



                                JSONArray produk = jsonObject.getJSONArray("produk");
                                for (int j = 0; j<produk.length(); j++){
                                    JSONObject jsonObject1 = produk.getJSONObject(j);
                                    data.put("nama_produk", jsonObject1.getString("nama_produk"));
                                    data.put("gambar", jsonObject1.getString("image_o"));
                                    data.put("variasi", jsonObject1.getString("ket2"));
                                    data.put("jumlah", jsonObject1.getString("jumlah"));
                                }

                                dataDetailPenjemputan.add(data);
                            }

                            Log.d("dataDetailPenjemputan", "onResponse: "+dataDetailPenjemputan);
                            recyclerVendor.setHasFixedSize(true);
                            recyclerVendor.setLayoutManager(new LinearLayoutManager(DetailPenjemputanActivity.this));
                            DetailVendorAdapter adapterVendor = new DetailVendorAdapter(DetailPenjemputanActivity.this, dataDetailPenjemputan, DetailPenjemputanActivity.this);
                            recyclerVendor.setAdapter(adapterVendor);

                            recyclerProduk.setHasFixedSize(true);
                            recyclerProduk.setLayoutManager(new LinearLayoutManager(DetailPenjemputanActivity.this));
                            DetailProdukAdapter adapterProduk = new DetailProdukAdapter(DetailPenjemputanActivity.this, dataDetailPenjemputan);
                            recyclerProduk.setAdapter(adapterProduk);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        dataDetailPenjemputan.clear();

                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail

                            // get parsed error object (If ApiError is your class)
                            Toast.makeText(DetailPenjemputanActivity.this, "Gagal mendapatkan data.", Toast.LENGTH_SHORT).show();
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            if (anError.getErrorDetail().equals("connectionError")){
                                Toast.makeText(DetailPenjemputanActivity.this, "Tidak ada koneksi internet.", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(DetailPenjemputanActivity.this, "Gagal mendapatkan data.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
}