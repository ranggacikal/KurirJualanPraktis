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

public class ProdukPenjemputanReturAdapter extends RecyclerView.Adapter<ProdukPenjemputanReturAdapter.ProdukPenjemputanReturViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> listProdukPenjemputanRetur = new ArrayList<>();
    private Pref pref;

    public ProdukPenjemputanReturAdapter(Context context, ArrayList<HashMap<String, String>> listProdukPenjemputanRetur) {
        this.context = context;
        this.listProdukPenjemputanRetur = listProdukPenjemputanRetur;
    }

    @NonNull
    @Override
    public ProdukPenjemputanReturViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_produk, parent, false);
        return new ProdukPenjemputanReturViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukPenjemputanReturViewHolder holder, int position) {

        HashMap<String, String> item = new HashMap<>();
        item = this.listProdukPenjemputanRetur.get(position);
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
        return (null != listProdukPenjemputanRetur ? listProdukPenjemputanRetur.size() : 0);
    }

    public class ProdukPenjemputanReturViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDetailProduk;
        TextView txtNamaProduk, txtVariasi, txtJumlah;

        public ProdukPenjemputanReturViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDetailProduk = itemView.findViewById(R.id.img_detail_produk);
            txtNamaProduk = itemView.findViewById(R.id.text_nama_detail_produk);
            txtVariasi = itemView.findViewById(R.id.text_variasi_detail_produk);
            txtJumlah = itemView.findViewById(R.id.text_jumlah_detail_produk);
        }
    }
}
