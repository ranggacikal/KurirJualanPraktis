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
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.kurirjualanpraktis.adapter.DetailPenjemputanReturAdapter;
import com.example.kurirjualanpraktis.adapter.DetailProdukAdapter;
import com.example.kurirjualanpraktis.adapter.DetailVendorAdapter;
import com.example.kurirjualanpraktis.adapter.ProdukPenjemputanReturAdapter;
import com.example.kurirjualanpraktis.sharedPreferences.SharedPrefManager;
import com.example.kurirjualanpraktis.sharedPreferences.loginuser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class DetailPenjemputanReturActivity extends AppCompatActivity {

    ImageView imgBack;
    RecyclerView recyclerPenjemputanRetur, recyclerProduk;
    Button btnUpdate;

    loginuser user;

    ArrayList<HashMap<String, String>> dataDetailPenjemputanRetur = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penjemputan_retur);

        user = SharedPrefManager.getInstance(DetailPenjemputanReturActivity.this).getUser();
        AndroidNetworking.initialize(DetailPenjemputanReturActivity.this.getApplicationContext());

        imgBack = findViewById(R.id.img_back_detail_penjemputan_retur);
        recyclerPenjemputanRetur = findViewById(R.id.recycler_detail_penjemputan_retur);
        recyclerProduk = findViewById(R.id.recycler_detail_produk_penjemputan_retur);
        btnUpdate = findViewById(R.id.btn_update_penjemputan_retur);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerPenjemputanRetur.setHasFixedSize(true);
        recyclerProduk.setHasFixedSize(true);

        recyclerPenjemputanRetur.setLayoutManager(new LinearLayoutManager(DetailPenjemputanReturActivity.this));
        recyclerProduk.setLayoutManager(new LinearLayoutManager(DetailPenjemputanReturActivity.this));

        loadDetailPenjemputanRetur();
    }

    private void loadDetailPenjemputanRetur() {

        ProgressDialog progressDialog = new ProgressDialog(DetailPenjemputanReturActivity.this);
        progressDialog.setTitle("Memuat Data");
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        String url = "https://jualanpraktis.net/android/kurir/detail-penjemputan-retur.php";

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.post(url)
                .addBodyParameter("idtrx", getIntent().getStringExtra("id_transaksi"))
                .setTag(DetailPenjemputanReturActivity.this)
                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressDialog.dismiss();
                        dataDetailPenjemputanRetur.clear();
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

                                dataDetailPenjemputanRetur.add(data);
                            }

                            DetailPenjemputanReturAdapter adapterDetail = new DetailPenjemputanReturAdapter(DetailPenjemputanReturActivity.this, dataDetailPenjemputanRetur);
                            recyclerPenjemputanRetur.setAdapter(adapterDetail);

                            ProdukPenjemputanReturAdapter adapterProduk = new ProdukPenjemputanReturAdapter(DetailPenjemputanReturActivity.this, dataDetailPenjemputanRetur);
                            recyclerProduk.setAdapter(adapterProduk);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        dataDetailPenjemputanRetur.clear();

                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail

                            // get parsed error object (If ApiError is your class)
                            Toast.makeText(DetailPenjemputanReturActivity.this, "Gagal mendapatkan data.", Toast.LENGTH_SHORT).show();
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            if (anError.getErrorDetail().equals("connectionError")){
                                Toast.makeText(DetailPenjemputanReturActivity.this, "Tidak ada koneksi internet.", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(DetailPenjemputanReturActivity.this, "Gagal mendapatkan data.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
}