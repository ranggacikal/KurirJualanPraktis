package com.example.kurirjualanpraktis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kurirjualanpraktis.DetailPenjemputanActivity;
import com.example.kurirjualanpraktis.DetailPenjemputanReturActivity;
import com.example.kurirjualanpraktis.R;
import com.example.kurirjualanpraktis.sharedPreferences.Pref;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailPenjemputanReturAdapter extends RecyclerView.Adapter<DetailPenjemputanReturAdapter.DetailPenjemputanReturVuewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> listDetailPenjemputanRetur = new ArrayList<>();
    DetailPenjemputanReturActivity detailPenjemputanReturActivity;
    private Pref pref;

    public DetailPenjemputanReturAdapter(Context context, ArrayList<HashMap<String, String>> listDetailPenjemputanRetur, DetailPenjemputanReturActivity detailPenjemputanReturActivity) {
        this.context = context;
        this.listDetailPenjemputanRetur = listDetailPenjemputanRetur;
        this.detailPenjemputanReturActivity = detailPenjemputanReturActivity;
    }

    @NonNull
    @Override
    public DetailPenjemputanReturVuewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_vendor, parent, false);
        return new DetailPenjemputanReturVuewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPenjemputanReturVuewHolder holder, int position) {

        HashMap<String, String> item = new HashMap<>();
        item = this.listDetailPenjemputanRetur.get(position);
        pref = new Pref(context.getApplicationContext());

        String nama_toko = item.get("nama_toko");
        String nama_pemilik = item.get("nama_penerima");
        String no_hp = item.get("no_hp");
        String alamat = item.get("alamat");
        String kecamatan = item.get("kecamatan");
        String kota = item.get("kota");
        String provinsi = item.get("provinsi");
        String id_transaksi = item.get("id_transaksi");

        detailPenjemputanReturActivity.id_member = item.get("id_member");
        detailPenjemputanReturActivity.id_pengiriman = item.get("id_pengiriman");

        holder.lblAlamatVendor.setText("Alamat");
        holder.relativeNamaToko.setVisibility(View.GONE);

        holder.txtIdTransaksi.setText(id_transaksi);
        holder.txtNamaPemilik.setText(nama_pemilik);
        holder.txtNoHp.setText(no_hp);
        holder.txtAlamat.setText(alamat+", "+kecamatan+", "+kota+", "+provinsi);

    }

    @Override
    public int getItemCount() {
        return (null != listDetailPenjemputanRetur ? listDetailPenjemputanRetur.size() : 0);
    }

    public class DetailPenjemputanReturVuewHolder extends RecyclerView.ViewHolder {

        TextView txtIdTransaksi, txtNamaPemilik, txtNoHp, txtAlamat, txtNamaToko;
        RelativeLayout relativeNamaToko;
        TextView lblAlamatVendor;

        public DetailPenjemputanReturVuewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdTransaksi = itemView.findViewById(R.id.text_id_transaksi_detail_vendor);
            txtNamaPemilik = itemView.findViewById(R.id.text_nama_pemilik_detail_vendor);
            txtNoHp = itemView.findViewById(R.id.text_nohp_detail_vendor);
            txtAlamat = itemView.findViewById(R.id.text_alamat_detail_vendor);
            txtNamaToko = itemView.findViewById(R.id.text_nama_toko_detail_vendor);
            relativeNamaToko = itemView.findViewById(R.id.relative_nama_toko_vendor);
            lblAlamatVendor = itemView.findViewById(R.id.lbl_alamat_vendor);
        }
    }
}
