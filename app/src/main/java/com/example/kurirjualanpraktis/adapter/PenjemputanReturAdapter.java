package com.example.kurirjualanpraktis.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kurirjualanpraktis.DetailPenjemputanReturActivity;
import com.example.kurirjualanpraktis.R;
import com.example.kurirjualanpraktis.sharedPreferences.Pref;

import java.util.ArrayList;
import java.util.HashMap;

public class PenjemputanReturAdapter extends RecyclerView.Adapter<PenjemputanReturAdapter.PenjemputanReturViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> listPenjemputanRetur = new ArrayList<>();
    private Pref pref;

    public PenjemputanReturAdapter(Context context, ArrayList<HashMap<String, String>> listPenjemputanRetur) {
        this.context = context;
        this.listPenjemputanRetur = listPenjemputanRetur;
    }

    @NonNull
    @Override
    public PenjemputanReturViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_penjemputan, parent, false);
        return new PenjemputanReturViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PenjemputanReturViewHolder holder, int position) {

        HashMap<String, String> item = new HashMap<>();
        item = this.listPenjemputanRetur.get(position);
        pref = new Pref(context.getApplicationContext());

        String nama_penerima = item.get("nama_penerima");
        String no_hp = item.get("no_hp");
        String alamat = item.get("alamat");
        String kecamatan = item.get("kecamatan");
        String kota = item.get("kota");
        String provinsi = item.get("provinsi");
        String id_transaksi = item.get("id_transaksi");

        holder.txtNamaToko.setText(nama_penerima);
        holder.txtNoHp.setText(no_hp);
        holder.txtAlamat.setText(alamat+", "+kecamatan+", "+kota+", "+provinsi);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPenjemputanReturActivity.class);
                intent.putExtra("id_transaksi", id_transaksi);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != listPenjemputanRetur ? listPenjemputanRetur.size() : 0);
    }

    public class PenjemputanReturViewHolder extends RecyclerView.ViewHolder {

        TextView txtNamaToko, txtNamaPenerima, txtNoHp, txtAlamat;

        public PenjemputanReturViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaToko = itemView.findViewById(R.id.text_nama_toko_penjemputan);
            txtNoHp = itemView.findViewById(R.id.text_nohp_penjemputan);
            txtAlamat = itemView.findViewById(R.id.text_alamat_penjemputan);
        }
    }
}
