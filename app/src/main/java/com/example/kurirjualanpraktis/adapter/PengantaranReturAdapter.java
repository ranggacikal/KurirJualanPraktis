package com.example.kurirjualanpraktis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kurirjualanpraktis.R;
import com.example.kurirjualanpraktis.sharedPreferences.Pref;

import java.util.ArrayList;
import java.util.HashMap;

public class PengantaranReturAdapter extends RecyclerView.Adapter<PengantaranReturAdapter.PengantaranReturViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> listPengantaranRetur = new ArrayList<>();
    private Pref pref;

    public PengantaranReturAdapter(Context context, ArrayList<HashMap<String, String>> listPengantaranRetur) {
        this.context = context;
        this.listPengantaranRetur = listPengantaranRetur;
    }

    @NonNull
    @Override
    public PengantaranReturViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_penjemputan, parent, false);
        return new PengantaranReturViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengantaranReturViewHolder holder, int position) {

        HashMap<String, String> item = new HashMap<>();
        item = this.listPengantaranRetur.get(position);
        pref = new Pref(context.getApplicationContext());

        String nama_penerima = item.get("nama_penerima");
        String no_hp = item.get("no_hp");
        String alamat = item.get("alamat");
        String kelurahan = item.get("kelurahan");
        String kecamatan = item.get("kecamatan");
        String kota = item.get("kota");
        String provinsi = item.get("provinsi");
        String id_transaksi = item.get("id_transaksi");

        holder.txtNamaToko.setText(nama_penerima);
        holder.txtNoHp.setText(no_hp);
        holder.txtAlamat.setText(alamat+", "+kelurahan+", "+kecamatan+", "+kota+", "+provinsi);

    }

    @Override
    public int getItemCount() {
        return (null != listPengantaranRetur ? listPengantaranRetur.size() : 0);
    }

    public class PengantaranReturViewHolder extends RecyclerView.ViewHolder {

        TextView txtNamaToko, txtNamaPenerima, txtNoHp, txtAlamat;

        public PengantaranReturViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaToko = itemView.findViewById(R.id.text_nama_toko_penjemputan);
            txtNoHp = itemView.findViewById(R.id.text_nohp_penjemputan);
            txtAlamat = itemView.findViewById(R.id.text_alamat_penjemputan);
        }
    }
}
