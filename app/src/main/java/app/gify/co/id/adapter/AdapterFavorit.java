package app.gify.co.id.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.gify.co.id.R;
import app.gify.co.id.modal.MadolCart;
import app.gify.co.id.modal.MadolFavorit;

public class AdapterFavorit extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<MadolFavorit> favorits;
    View view;
    Context context;

    public AdapterFavorit(ArrayList<MadolFavorit> favorits, Context context) {
        this.favorits = favorits;
        this.context = context;
    }

    class MyFav extends RecyclerView.ViewHolder {

        public MyFav(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_kado, parent, false);
        return new MyFav(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return favorits.size();
    }
}
