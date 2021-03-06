package app.gify.co.id.publicload.ui.favorit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.gify.co.id.R;
import app.gify.co.id.adapter.AdapterFavorit;
import app.gify.co.id.adapter.AdapterListKado;
import app.gify.co.id.modal.MadolFavorit;
import app.gify.co.id.modal.MadolKado;

import static app.gify.co.id.baseurl.UrlJson.GETBARANG;
import static app.gify.co.id.baseurl.UrlJson.GETFAV;


public class FavoritFragment extends Fragment {

    private FavoritViewModel favoritViewModel;
    AdapterFavorit adapterFavorit;
    RecyclerView recyclerView;
    ArrayList<MadolFavorit> kados;
    GridLayoutManager glm;
    SharedPreferences preferences;
    String uid, id_barang;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        favoritViewModel=ViewModelProviders.of(this).get(FavoritViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorit, container, false);

        recyclerView = root.findViewById(R.id.recyclerfavorit);

        kados = new ArrayList<>();
        glm = new GridLayoutManager(getActivity(), 2);

        recyclerView.setLayoutManager(glm);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        uid = preferences.getString("uid", "");
        getFavorit();
        return root;
    }

    private void getBarang() {
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, GETBARANG, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("YukNgaji");
                    for (int a = 0; a < array.length(); a++){
                        JSONObject object = array.getJSONObject(a);
                            String nama = object.getString("nama");
                            int harga = object.getInt("harga");
                            String gambar = object.getString("photo");
                            String tipe = object.getString("kode_barang");
                            String desc = object.getString("deskripsi");
                            String idbarang = object.getString("id");
                            Log.d("idbarang", "onResponse: " + idbarang + " s " +id_barang);
                            if (idbarang.equalsIgnoreCase(id_barang)){
                                Log.d("idbarangif", "onResponse: " + idbarang + id_barang);
                                MadolFavorit madolFavorit = new MadolFavorit(gambar, harga, nama, tipe, desc, idbarang);
                                kados.add(madolFavorit);
                                adapterFavorit = new AdapterFavorit(kados, getContext());
                                recyclerView.setAdapter(adapterFavorit);
                            }
                            Log.d("listkadoharga", "onResponse: " + harga + tipe + " s " + idbarang);

                    }
                } catch (JSONException e) {
                    Log.d("jsonbarangerror", "onResponse: " + e.getMessage());
                    e.printStackTrace();
                }

            }
        }, error -> {
            Log.d("errorlistkadojson", "getBarang: " + error.getMessage());
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(objectRequest);
    }

    private void getFavorit(){
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, GETFAV,null, response -> {
            try {
                JSONArray array = response.getJSONArray("YukNgaji");
                for (int a = 0; a < array.length(); a++){
                    JSONObject object = array.getJSONObject(a);
                    String id_tetap = object.getString("id_tetap");
                    Log.d("idbarangfor", "getFavorit: " + id_tetap + " s " + uid);
                    if (id_tetap.contains(uid)){
                        id_barang = object.getString("id_barang");
                        Log.d("idbarangku", "getFavorit: " + id_tetap + uid + id_barang);
                        getBarang();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.d("adapterlist", "getFavorit: " + error.getMessage());
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(objectRequest);
    }
}
