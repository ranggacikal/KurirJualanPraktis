package com.example.kurirjualanpraktis.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kurirjualanpraktis.DetailPenjemputanActivity;
import com.example.kurirjualanpraktis.R;
import com.example.kurirjualanpraktis.sharedPreferences.Pref;

import java.util.ArrayList;
import java.util.HashMap;

public class PenjemputanAdapter extends RecyclerView.Adapter<PenjemputanAdapter.PenjemputanViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> listPenjemputan = new ArrayList<>();
    private Pref pref;

    public PenjemputanAdapter(Context context, ArrayList<HashMap<String, String>> listPenjemputan) {
        this.context = context;
        this.listPenjemputan = listPenjemputan;
    }

    @NonNull
    @Override
    public PenjemputanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_penjemputan, parent, false);
        return new PenjemputanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PenjemputanViewHolder holder, int position) {
        HashMap<String, String> item = new HashMap<>();
        item = this.listPenjemputan.get(position);
        pref = new Pref(context.getApplicationContext());

        String nama_toko = item.get("nama_toko");
        String nama_penerima = item.get("nama_penerima");
        String no_hp = item.get("no_hp");
        String alamat = item.get("alamat");
        String kelurahan = item.get("kelurahan");
        String kecamatan = item.get("kecamatan");
        String kota = item.get("kota");
        String provinsi = item.get("provinsi");
        String id_pengiriman = item.get("id_pengiriman");

        holder.txtNamaToko.setText(nama_toko);
        holder.txtNoHp.setText(no_hp);
        holder.txtAlamat.setText(alamat+", "+kelurahan+", "+kecamatan+", "+kota+", "+provinsi);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPenjemputanActivity.class);
                intent.putExtra("id_pengiriman", id_pengiriman);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != listPenjemputan ? listPenjemputan.size() : 0);
    }

    public class PenjemputanViewHolder extends RecyclerView.ViewHolder {

        TextView txtNamaToko, txtNamaPenerima, txtNoHp, txtAlamat;

        public PenjemputanViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaToko = itemView.findViewById(R.id.text_nama_toko_penjemputan);
            txtNoHp = itemView.findViewById(R.id.text_nohp_penjemputan);
            txtAlamat = itemView.findViewById(R.id.text_alamat_penjemputan);
        }
    }
}
