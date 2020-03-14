package app.gify.co.id.publicload.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.gify.co.id.R;


public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private HomeViewModel homeViewModel;
    String kadobuatsiapaku, acaraapaku, hariku, bulanku, tahunku;
    NumberPicker numberpicker;
    Spinner kadobuatsiapa, acarapa, hari, bulan, tahun;
    HintArrayAdapter hintAdapter, hintadapterku, hintadapterbulan, hintadaptertahun, hintadapterhari;
    String[] kadolist;
    Button carikado;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel=ViewModelProviders.of(this).get(HomeViewModel.class);
        View root=inflater.inflate(R.layout.fragment_home, container, false);
        kadobuatsiapa=root.findViewById(R.id.buatSiapaCari);
        acarapa=root.findViewById(R.id.acaraApaCari);
        hari=root.findViewById(R.id.tanggalCari);
        bulan=root.findViewById(R.id.bulanCari);
        tahun=root.findViewById(R.id.tahunCari);
        carikado=root.findViewById(R.id.cariKado);
        hintAdapter = new HintArrayAdapter<String>(getContext(), 0);
        hintadapterku = new HintArrayAdapter<String>(getContext(), 0);
        hintadapterhari = new HintArrayAdapter<String>(getContext(), 0);
        hintadapterbulan = new HintArrayAdapter<String>(getContext(), 0);
        hintadaptertahun = new HintArrayAdapter<String>(getContext(), 0);
        hintAdapter.add("hint");
        hintadapterku.add("hint");
        hintadapterbulan.add("Januari");
        hintadapterbulan.add("Februari");
        hintadapterbulan.add("Maret");
        hintadapterbulan.add("April");
        hintadapterbulan.add("Mei");
        hintadapterbulan.add("Juni");
        hintadapterbulan.add("Juli");
        hintadapterbulan.add("Agustus");
        hintadapterbulan.add("September");
        hintadapterbulan.add("October");
        hintadapterbulan.add("November");
        hintadapterbulan.add("December");
        getkategori();
        getAcara();
        for (int a = 0; a <= 31; a++){
            hintadapterhari.add(a);
        }
        for (int a = 1930; a <= 2020; a++){
            hintadaptertahun.add(a);
        }
        hari.setAdapter(hintadapterhari);
        bulan.setAdapter(hintadapterbulan);
        tahun.setAdapter(hintadaptertahun);
        hari.setSelection(0, false);
        bulan.setSelection(0, false);
        tahun.setSelection(0, false);
        carikado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
            }
        });
        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class HintArrayAdapter<T> extends ArrayAdapter<T> {

        Context mContext;

        public HintArrayAdapter(Context context, int resource) {
            super(context, resource);
            this.mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.spinnner, parent, false);
            TextView texview = (TextView) view.findViewById(android.R.id.text1);

            if(position == 0) {
                texview.setText("-- pilih --");
                texview.setTextColor(Color.parseColor("#b4b3b3"));
                texview.setHint(getItem(position).toString()); //"Hint to be displayed"
            } else {
                texview.setText(getItem(position).toString());
            }

            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view;

            if(position == 0){
                view = inflater.inflate(R.layout.spinner_hint_list_item_layout, parent, false); // Hide first row
            } else {
                view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
                TextView texview = (TextView) view.findViewById(android.R.id.text1);
                texview.setText(getItem(position).toString());
            }

            return view;
        }
    }

    public void getkategori(){
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.3.156/gify/api/kado.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("bambang", "onResponse: " + response.toString());
                try {
                    JSONArray array = response.getJSONArray("YukNgaji");
                    Log.d("arraku", "onResponse: " + array.length());
                    for (int a = 0;a < array.length() ; a++){

                        JSONObject object = array.getJSONObject(a);
                        String tes = object.getString("id");
                        String ku = object.getString("sub_category");
                        hintAdapter.add(ku);
                        kadobuatsiapa.setAdapter(hintAdapter);

                        //spinner1.setSelection(0); //display hint. Actually you can ignore it, because the default is already 0
                        kadobuatsiapa.setSelection(0, false); //use this if don't want to onItemClick called for the hint

                        kadobuatsiapa.setOnItemSelectedListener(HomeFragment.this);
                        Log.d("makansamaale", "onResponse: "+tes+" "+ku);
                    }
                } catch (JSONException e) {
                    Log.d("ejson", "onResponse: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onerror", "onErrorResponse: " + error.getMessage());

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Log.d("queue", "getkategori: " + objectRequest + queue);
        queue.add(objectRequest);
    }

    private void getAcara(){
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.3.156/gify/api/acara.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("YukNgaji");
                    for (int a = 0; a < array.length(); a++){
                        JSONObject object = array.getJSONObject(a);
                        String acara = object.getString("untuk_acara");
                        Log.d("acaraku", "onResponse: " + acara);
                        hintadapterku.add(acara);

                        acarapa.setAdapter(hintadapterku);

                        acarapa.setSelection(0, false);

                    }
                } catch (JSONException e) {
                    Log.d("acarakus", "onResponse: " );
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("acarakuk", "onResponse: " + error.getMessage());

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(objectRequest);
    }
}
