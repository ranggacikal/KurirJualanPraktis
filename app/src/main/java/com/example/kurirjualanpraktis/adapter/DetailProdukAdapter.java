package com.example.kurirjualanpraktis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kurirjualanpraktis.R;
import com.example.kurirjualanpraktis.sharedPreferences.Pref;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailProdukAdapter extends RecyclerView.Adapter<DetailProdukAdapter.DetailProdukViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> listDetailProduk = new ArrayList<>();
    private Pref pref;

    public DetailProdukAdapter(Context context, ArrayList<HashMap<String, String>> listDetailProduk) {
        this.context = context;
        this.listDetailProduk = listDetailProduk;
    }

    @NonNull
    @Override
    public DetailProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_produk, parent, false);
        return new DetailProdukViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailProdukViewHolder holder, int position) {
        HashMap<String, String> item = new HashMap<>();
        item = this.listDetailProduk.get(position);
        pref = new Pref(context.getApplicationContext());

        String image = item.get("gambar");
        String nama_produk = item.get("nama_produk");
        String variasi = item.get("variasi");
        String jumlah = item.get("jumlah");

        holder.txtNamaProduk.setText(nama_produk);
        holder.txtVariasi.setText(variasi);
        holder.txtJumlah.setText(jumlah);

        Glide.with(context)
                .load(image)
                .error(R.drawable.icon_app)
                .into(holder.imgDetailProduk);

    }

    @Override
    public int getItemCount() {
        return (null != listDetailProduk ? listDetailProduk.size() : 0);
    }

    public class DetailProdukViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDetailProduk;
        TextView txtNamaProduk, txtVariasi, txtJumlah;

        public DetailProdukViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDetailProduk = itemView.findViewById(R.id.img_detail_produk);
            txtNamaProduk = itemView.findViewById(R.id.text_nama_detail_produk);
            txtVariasi = itemView.findViewById(R.id.text_variasi_detail_produk);
            txtJumlah = itemView.findViewById(R.id.text_jumlah_detail_produk);
        }
    }
}
