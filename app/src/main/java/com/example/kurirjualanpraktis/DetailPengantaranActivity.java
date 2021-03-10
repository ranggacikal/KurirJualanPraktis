package com.example.kurirjualanpraktis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.kurirjualanpraktis.adapter.DetailPenerimaAdapter;
import com.example.kurirjualanpraktis.adapter.DetailProdukAdapter;
import com.example.kurirjualanpraktis.adapter.DetailVendorAdapter;
import com.example.kurirjualanpraktis.adapter.ProdukPengantaranAdapter;
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

public class DetailPengantaranActivity extends AppCompatActivity {

    Button btnTerima, btnTolak;
    RecyclerView recyclerDetailPenerima, recyclerDetailProduk;
    ImageView imgBack;

    loginuser user;

    ArrayList<HashMap<String, String>> dataPengantaran = new ArrayList<>();

    DetailPengantaranActivity detailPengantaranActivity;

    public String id_transaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengantaran);

        user = SharedPrefManager.getInstance(DetailPengantaranActivity.this).getUser();
        AndroidNetworking.initialize(DetailPengantaranActivity.this.getApplicationContext());

        btnTolak = findViewById(R.id.btn_ditolak_pengantaran);
        btnTerima = findViewById(R.id.btn_diterima_pengantaran);
        recyclerDetailPenerima = findViewById(R.id.recycler_detail_penerima);
        recyclerDetailProduk = findViewById(R.id.recycler_detail_produk_pengantaran);
        imgBack = findViewById(R.id.img_back_detail_pengantaran);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        id_transaksi = getIntent().getStringExtra("id_transaksi");

        btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terimaPengantaran(id_transaksi);
            }
        });

        btnTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tolakPengantaran(id_transaksi);
            }
        });

        loadDetailPengantaran();

    }

    private void tolakPengantaran(String id_transaksi) {

        String host = "https://jualanpraktis.net/android/kurir/update-pengantaran-ditolak.php";

        ProgressDialog progressDialog = new ProgressDialog(DetailPengantaranActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Update Status Pengantaran");
        progressDialog.setCancelable(false);
        progressDialog.show();


        Map<String, String> params = new HashMap<String, String>();
        params.put("id_transaksi", id_transaksi);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        AndroidNetworking.post(host)
                .addBodyParameter(params)
                .setTag(DetailPengantaranActivity.this)
                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Toast.makeText(DetailPengantaranActivity.this, "Berhasil Update Status Pengantaran Ditolak", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "Gagal Update Status Pengantaran Ditolak", Toast.LENGTH_SHORT).show();
                    }

                });

    }

    private void terimaPengantaran(String id_transaksi) {

        String host = "https://jualanpraktis.net/android/kurir/update-pengantaran-diterima.php";

        ProgressDialog progressDialog = new ProgressDialog(DetailPengantaranActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Update Status Pengantaran");
        progressDialog.setCancelable(false);
        progressDialog.show();


        Map<String, String> params = new HashMap<String, String>();
        params.put("id_transaksi", id_transaksi);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        AndroidNetworking.post(host)
                .addBodyParameter(params)
                .setTag(DetailPengantaranActivity.this)
                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Toast.makeText(DetailPengantaranActivity.this, "Berhasil Update Status Pengantaran Diterima", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "Gagal Update Status Pengantaran Diterima", Toast.LENGTH_SHORT).show();
                    }

                });

    }

    private void loadDetailPengantaran() {

        ProgressDialog progressDialog = new ProgressDialog(DetailPengantaranActivity.this);
        progressDialog.setTitle("Memuat Data");
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        String url = "https://jualanpraktis.net/android/kurir/detail-pengantaran.php";

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.post(url)
                .addBodyParameter("idtrx", getIntent().getStringExtra("id_transaksi"))
                .setTag(DetailPengantaranActivity.this)
                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressDialog.dismiss();
                        dataPengantaran.clear();
                        try {
                            JSONArray array = response.getJSONArray("data");
                            for (int i = 0;i<array.length();i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                HashMap<String,String> data = new HashMap<>();
                                data.put("id_pengiriman",jsonObject.getString("id_pengiriman"));
                                data.put("id_member",jsonObject.getString("id_member"));
                                data.put("id_transaksi",jsonObject.getString("id_transaksi"));
                                data.put("nama_penerima",jsonObject.getString("nama_penerima"));
                                data.put("provinsi",jsonObject.getString("province"));
                                data.put("kota",jsonObject.getString("city_name"));
                                data.put("kecamatan",jsonObject.getString("subdistrict_name"));
                                data.put("alamat",jsonObject.getString("alamat"));
                                data.put("no_hp",jsonObject.getString("no_hp"));
                                data.put("ongkos",jsonObject.getString("ongkos"));
                                data.put("total_bayar",jsonObject.getString("total_bayar"));
                                data.put("grand_total",jsonObject.getString("grand_total"));



                                JSONArray produk = jsonObject.getJSONArray("produk");
                                for (int j = 0; j<produk.length(); j++){
                                    JSONObject jsonObject1 = produk.getJSONObject(j);
                                    data.put("nama_produk", jsonObject1.getString("nama_produk"));
                                    data.put("gambar", jsonObject1.getString("image_o"));
                                    data.put("variasi", jsonObject1.getString("ket2"));
                                    data.put("jumlah", jsonObject1.getString("jumlah"));
                                }

                                dataPengantaran.add(data);
                            }

                            recyclerDetailPenerima.setHasFixedSize(true);
                            recyclerDetailPenerima.setLayoutManager(new LinearLayoutManager(DetailPengantaranActivity.this));
                            DetailPenerimaAdapter adapterPenerima = new DetailPenerimaAdapter(DetailPengantaranActivity.this, dataPengantaran);
                            recyclerDetailPenerima.setAdapter(adapterPenerima);

                            recyclerDetailProduk.setHasFixedSize(true);
                            recyclerDetailProduk.setLayoutManager(new LinearLayoutManager(DetailPengantaranActivity.this));
                            ProdukPengantaranAdapter adapterProduk = new ProdukPengantaranAdapter(
                                    DetailPengantaranActivity.this, dataPengantaran);
                            recyclerDetailProduk.setAdapter(adapterProduk);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        dataPengantaran.clear();

                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail

                            // get parsed error object (If ApiError is your class)
                            Toast.makeText(DetailPengantaranActivity.this, "Gagal mendapatkan data.", Toast.LENGTH_SHORT).show();
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            if (anError.getErrorDetail().equals("connectionError")){
                                Toast.makeText(DetailPengantaranActivity.this, "Tidak ada koneksi internet.", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(DetailPengantaranActivity.this, "Gagal mendapatkan data.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
}