package com.example.kurirjualanpraktis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kurirjualanpraktis.DetailPengantaranReturActivity;
import com.example.kurirjualanpraktis.R;
import com.example.kurirjualanpraktis.sharedPreferences.Pref;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class PenerimaDetailPengantaranReturAdapter extends RecyclerView.Adapter<PenerimaDetailPengantaranReturAdapter.MyViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> listDetailPenerimaRetur = new ArrayList<>();
    DetailPengantaranReturActivity detailPengantaranReturActivity;
    private Pref pref;

    public PenerimaDetailPengantaranReturAdapter(Context context, ArrayList<HashMap<String, String>> listDetailPenerimaRetur, DetailPengantaranReturActivity detailPengantaranReturActivity) {
        this.context = context;
        this.listDetailPenerimaRetur = listDetailPenerimaRetur;
        this.detailPengantaranReturActivity = detailPengantaranReturActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_penerima, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HashMap<String, String> item = new HashMap<>();
        item = this.listDetailPenerimaRetur.get(position);
        pref = new Pref(context.getApplicationContext());

        holder.relativeTotal.setVisibility(View.GONE);
        holder.relativeOngkos.setVisibility(View.GONE);
        holder.relativeGrand.setVisibility(View.GONE);

        detailPengantaranReturActivity.id_transaksi = item.get("id_transaksi");
        detailPengantaranReturActivity.id_member = item.get("id_member");

        holder.txtIdTransaksi.setText(item.get("id_transaksi"));
        holder.txtNamaPenerima.setText(item.get("nama_penerima"));
        holder.txtNoHp.setText(item.get("no_hp"));
        holder.txtAlamat.setText(item.get("alamat")+", "+item.get("kelurahan")+", "+item.get("kecamatan")+", "+item.get("kota")+", "
                +item.get("provinsi"));

    }

    @Override
    public int getItemCount() {
        return (null != listDetailPenerimaRetur ? listDetailPenerimaRetur.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdTransaksi, txtNamaPenerima, txtNoHp, txtAlamat, txtTotalBayar, txtOngkos, txtGrandTotal;
        RelativeLayout relativeTotal, relativeOngkos, relativeGrand;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdTransaksi = itemView.findViewById(R.id.text_id_transaksi_detail_penerima);
            txtNamaPenerima = itemView.findViewById(R.id.text_nama_detail_penerima);
            txtNoHp = itemView.findViewById(R.id.text_nohp_detail_penerima);
            txtAlamat = itemView.findViewById(R.id.text_alamat_detail_penerima);
            txtTotalBayar = itemView.findViewById(R.id.text_total_bayar_detail_penerima);
            txtOngkos = itemView.findViewById(R.id.text_ongkos_detail_penerima);
            txtGrandTotal = itemView.findViewById(R.id.text_grand_total_detail_penerima);
            relativeTotal = itemView.findViewById(R.id.relative_total_bayar_penerima);
            relativeOngkos = itemView.findViewById(R.id.relative_ongkos_penerima);
            relativeGrand = itemView.findViewById(R.id.relative_grand_total_penerima);
        }
    }
}
