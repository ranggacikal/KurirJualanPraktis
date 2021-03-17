package com.example.kurirjualanpraktis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.kurirjualanpraktis.adapter.DetailPenerimaAdapter;
import com.example.kurirjualanpraktis.adapter.PenerimaDetailPengantaranReturAdapter;
import com.example.kurirjualanpraktis.adapter.ProdukPengantaranAdapter;
import com.example.kurirjualanpraktis.adapter.ProdukPengantaranReturAdapter;
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

public class DetailPengantaranReturActivity extends AppCompatActivity {

    Button btnTerima, btnTolak;
    RecyclerView recyclerDetailPenerima, recyclerDetailProduk;
    ImageView imgBack;

    loginuser user;

    LinearLayout linearButton;
    Button btnUpdate;

    ArrayList<HashMap<String, String>> dataPengantaranRetur = new ArrayList<>();

    DetailPengantaranReturActivity detailPengantaranReturActivity;

    public String id_transaksi;
    public String id_member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengantaran_retur);

        user = SharedPrefManager.getInstance(DetailPengantaranReturActivity.this).getUser();
        AndroidNetworking.initialize(DetailPengantaranReturActivity.this.getApplicationContext());

        btnTerima = findViewById(R.id.btn_diterima_pengantaran_retur);
        btnTolak = findViewById(R.id.btn_ditolak_pengantaran_retur);
        recyclerDetailPenerima = findViewById(R.id.recycler_detail_penerima_pengantaran_retur);
        recyclerDetailProduk = findViewById(R.id.recycler_detail_produk_pengantaran_retur);
        imgBack = findViewById(R.id.img_back_detail_pengantaran_retur);
        linearButton = findViewById(R.id.linear_button_detail_pengantaran_retur);
        btnUpdate = findViewById(R.id.btn_update_pengantaran_retur);

        linearButton.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.VISIBLE);

        recyclerDetailPenerima.setHasFixedSize(true);
        recyclerDetailPenerima.setLayoutManager(new LinearLayoutManager(DetailPengantaranReturActivity.this));

        recyclerDetailProduk.setHasFixedSize(true);
        recyclerDetailProduk.setLayoutManager(new LinearLayoutManager(DetailPengantaranReturActivity.this));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePengantaranRetur();
            }
        });

        loadDetailPengantaranRetur();
    }

    private void updatePengantaranRetur() {

        String host = "https://jualanpraktis.net/android/kurir/update-pengantaran-retur.php";

        ProgressDialog progressDialog = new ProgressDialog(DetailPengantaranReturActivity.this);
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
                .setTag(DetailPengantaranReturActivity.this)
                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Toast.makeText(DetailPengantaranReturActivity.this, "Berhasil Update Status Pengantaran retur", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "Gagal Update Status Pengantaran Retur", Toast.LENGTH_SHORT).show();
                    }

                });

    }

    private void loadDetailPengantaranRetur() {

        ProgressDialog progressDialog = new ProgressDialog(DetailPengantaranReturActivity.this);
        progressDialog.setTitle("Memuat Data");
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        String url = "https://jualanpraktis.net/android/kurir/detail-pengantaran-retur.php";

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.post(url)
                .addBodyParameter("id_pengiriman", getIntent().getStringExtra("id_pengiriman"))
                .setTag(DetailPengantaranReturActivity.this)
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
                                data.put("id_customer",jsonObject.getString("id_customer"));
                                data.put("id_member",jsonObject.getString("id_member"));
                                data.put("id_transaksi",jsonObject.getString("id_transaksi"));
                                data.put("nama_penerima",jsonObject.getString("nama"));
                                data.put("provinsi",jsonObject.getString("province"));
                                data.put("kota",jsonObject.getString("city_name"));
                                data.put("kecamatan",jsonObject.getString("subdistrict_name"));
                                data.put("kelurahan",jsonObject.getString("kelurahan"));
                                data.put("alamat",jsonObject.getString("alamat"));
                                data.put("no_hp",jsonObject.getString("no_hp"));
                                data.put("nama_toko",jsonObject.getString("nama_company"));



                                JSONArray produk = jsonObject.getJSONArray("produk");
                                for (int j = 0; j<produk.length(); j++){
                                    JSONObject jsonObject1 = produk.getJSONObject(j);
                                    data.put("nama_produk", jsonObject1.getString("nama_produk"));
                                    data.put("gambar", jsonObject1.getString("image_o"));
                                    data.put("variasi", jsonObject1.getString("ket2"));
                                    data.put("jumlah", jsonObject1.getString("jumlah"));
                                }

                                dataPengantaranRetur.add(data);
                            }

                            //set aapter
                            PenerimaDetailPengantaranReturAdapter adapterPenerima = new PenerimaDetailPengantaranReturAdapter(DetailPengantaranReturActivity.this, dataPengantaranRetur, DetailPengantaranReturActivity.this);
                            recyclerDetailPenerima.setAdapter(adapterPenerima);

                            ProdukPengantaranReturAdapter adapterProduk = new ProdukPengantaranReturAdapter(DetailPengantaranReturActivity.this, dataPengantaranRetur);
                            recyclerDetailProduk.setAdapter(adapterProduk);

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
                            Toast.makeText(DetailPengantaranReturActivity.this, "Gagal mendapatkan data.", Toast.LENGTH_SHORT).show();
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            if (anError.getErrorDetail().equals("connectionError")){
                                Toast.makeText(DetailPengantaranReturActivity.this, "Tidak ada koneksi internet.", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(DetailPengantaranReturActivity.this, "Gagal mendapatkan data.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
}