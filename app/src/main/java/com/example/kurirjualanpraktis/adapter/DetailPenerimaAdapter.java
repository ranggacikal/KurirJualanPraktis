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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailPenerimaAdapter extends RecyclerView.Adapter<DetailPenerimaAdapter.DetailPenerimaViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> listDetailPenerima = new ArrayList<>();
    private Pref pref;

    public DetailPenerimaAdapter(Context context, ArrayList<HashMap<String, String>> listDetailPenerima) {
        this.context = context;
        this.listDetailPenerima = listDetailPenerima;
    }

    @NonNull
    @Override
    public DetailPenerimaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_penerima, parent, false);
        return new DetailPenerimaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPenerimaViewHolder holder, int position) {
        HashMap<String, String> item = new HashMap<>();
        item = this.listDetailPenerima.get(position);
        pref = new Pref(context.getApplicationContext());

        holder.txtIdTransaksi.setText(item.get("id_transaksi"));
        holder.txtNamaPenerima.setText(item.get("nama_penerima"));
        holder.txtNoHp.setText(item.get("no_hp"));
        holder.txtAlamat.setText(item.get("alamat")+", "+item.get("kecamatan")+", "+item.get("kota")+", "
                +item.get("provinsi"));
        holder.txtTotalBayar.setText("Rp" + NumberFormat.getInstance().format(Integer.parseInt(item.get("total_bayar"))));
        holder.txtOngkos.setText("Rp" + NumberFormat.getInstance().format(Integer.parseInt(item.get("ongkos"))));
        holder.txtGrandTotal.setText("Rp" + NumberFormat.getInstance().format(Integer.parseInt(item.get("grand_total"))));

    }

    @Override
    public int getItemCount() {
        return (null != listDetailPenerima ? listDetailPenerima.size() : 0);
    }

    public class DetailPenerimaViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdTransaksi, txtNamaPenerima, txtNoHp, txtAlamat, txtTotalBayar, txtOngkos, txtGrandTotal;

        public DetailPenerimaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdTransaksi = itemView.findViewById(R.id.text_id_transaksi_detail_penerima);
            txtNamaPenerima = itemView.findViewById(R.id.text_nama_detail_penerima);
            txtNoHp = itemView.findViewById(R.id.text_nohp_detail_penerima);
            txtAlamat = itemView.findViewById(R.id.text_alamat_detail_penerima);
            txtTotalBayar = itemView.findViewById(R.id.text_total_bayar_detail_penerima);
            txtOngkos = itemView.findViewById(R.id.text_ongkos_detail_penerima);
            txtGrandTotal = itemView.findViewById(R.id.text_grand_total_detail_penerima);
        }
    }
}
