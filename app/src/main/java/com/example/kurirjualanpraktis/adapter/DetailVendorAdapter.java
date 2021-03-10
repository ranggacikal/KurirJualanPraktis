package com.example.kurirjualanpraktis.adapter;

import android.content.Context;
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

public class DetailVendorAdapter extends RecyclerView.Adapter<DetailVendorAdapter.DetailVendorViewholder> {

    Context context;
    ArrayList<HashMap<String, String>> listDetail = new ArrayList<>();
    DetailPenjemputanActivity detailPenjemputanActivity;
    private Pref pref;

    public DetailVendorAdapter(Context context, ArrayList<HashMap<String, String>> listDetail, DetailPenjemputanActivity detailPenjemputanActivity) {
        this.context = context;
        this.listDetail = listDetail;
        this.detailPenjemputanActivity = detailPenjemputanActivity;
    }

    @NonNull
    @Override
    public DetailVendorViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_vendor, parent, false);
        return new DetailVendorViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailVendorViewholder holder, int position) {
        HashMap<String, String> item = new HashMap<>();
        item = this.listDetail.get(position);
        pref = new Pref(context.getApplicationContext());

        String nama_toko = item.get("nama_toko");
        String nama_pemilik = item.get("nama_pemilik");
        String no_hp = item.get("no_hp");
        String alamat = item.get("alamat");
        String kelurahan = item.get("kelurahan");
        String kecamatan = item.get("kecamatan");
        String kota = item.get("kota");
        String provinsi = item.get("provinsi");
        String id_transaksi = item.get("id_transaksi");

        detailPenjemputanActivity.id_transaksi = id_transaksi;
        detailPenjemputanActivity.id_member = item.get("id_member");

        holder.txtIdTransaksi.setText(id_transaksi);
        holder.txtNamaPemilik.setText(nama_pemilik);
        holder.txtNoHp.setText(no_hp);
        holder.txtAlamat.setText(alamat+", "+kelurahan+", "+kecamatan+", "+kota+", "+provinsi);
        holder.txtNamaToko.setText(nama_toko);

    }

    @Override
    public int getItemCount() {
        return (null != listDetail ? listDetail.size() : 0);
    }

    public class DetailVendorViewholder extends RecyclerView.ViewHolder {

        TextView txtIdTransaksi, txtNamaPemilik, txtNoHp, txtAlamat, txtNamaToko;

        public DetailVendorViewholder(@NonNull View itemView) {
            super(itemView);

            txtIdTransaksi = itemView.findViewById(R.id.text_id_transaksi_detail_vendor);
            txtNamaPemilik = itemView.findViewById(R.id.text_nama_pemilik_detail_vendor);
            txtNoHp = itemView.findViewById(R.id.text_nohp_detail_vendor);
            txtAlamat = itemView.findViewById(R.id.text_alamat_detail_vendor);
            txtNamaToko = itemView.findViewById(R.id.text_nama_toko_detail_vendor);
        }
    }
}
