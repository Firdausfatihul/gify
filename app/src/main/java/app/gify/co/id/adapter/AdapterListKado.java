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

        public MyKado(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart, parent, false);
        return new MyKado(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
