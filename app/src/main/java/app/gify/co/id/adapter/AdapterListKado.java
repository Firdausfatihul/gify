package app.gify.co.id.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.gify.co.id.R;
import app.gify.co.id.modal.MadolCart;
import app.gify.co.id.modal.MadolKado;

public class AdapterListKado extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<MadolKado> kados;
    View view;
    Context context;

    public AdapterListKado(ArrayList<MadolKado> kados, Context context) {
        this.kados = kados;
        this.context = context;
    }

    class MyKado extends RecyclerView.ViewHolder {

        ImageView photo, favorit;
        TextView harga, nama;

        public MyKado(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.fovoriteAdapter);
            favorit = itemView.findViewById(R.id.favoritbarang);
            harga = itemView.findViewById(R.id.hargaBarangFavoritAdapter);
            nama = itemView.findViewById(R.id.namabarangfavorit);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_barang_favorit, parent, false);
        return new MyKado(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyKado)holder).nama.setText(kados.get(position).getNama()+"("+kados.get(position).getKode()+")");
        ((MyKado)holder).harga.setText("Rp. " + kados.get(position).getHarga());
        if (kados.get(position).getGambar().isEmpty()){
            Toast.makeText(context, "tydac ada gambar", Toast.LENGTH_SHORT).show();
        }else {
            Picasso.get().load(kados.get(position).getGambar()).into(((MyKado)holder).photo);
        }
    }

    @Override
    public int getItemCount() {
        return kados.size();
    }
}
