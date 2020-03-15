package app.gify.co.id.publicload.ui.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import app.gify.co.id.R;
import app.gify.co.id.activity.List_Kado;


public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private HomeViewModel homeViewModel;
    String kadobuatsiapaku, acaraapaku, hariku, bulanku, tahunku;
    NumberPicker numberpicker;
    Spinner kadobuatsiapa, acarapa;
    private Calendar date;
    TextView tahun,hari, bulan;
    HintArrayAdapter hintAdapter, hintadapterku;
    String[] kadolist;
    Button carikado;

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        hari.setOnClickListener(view1 -> showdatedaypicker());

        getkategori();
        getAcara();

        tahun.setOnClickListener(view1 -> showdateyearpicker());

        bulan.setOnClickListener(view -> showdatemonthpicker());

        carikado.setOnClickListener(view1 ->  {
                Intent intent = new Intent(getContext(), List_Kado.class);
                startActivity(intent);
        });
        return root;
    }

    private void showdatemonthpicker(){
        final Calendar currentdate = Calendar.getInstance();
        date = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = ((datePicker, year, month, day) -> {
            Log.d("monthasda", "showdatemonthpicker: " + month);
            switch (month){
                case 0:
                    bulan.setText("Januari");
                    break;
                case 1:
                    bulan.setText("Februari");
                    break;
                case 2:
                    bulan.setText("Maret");
                    break;
                case 3:
                    bulan.setText("April");
                    break;
                case 4:
                    bulan.setText("Mei");
                    break;
                case 5:
                    bulan.setText("Juni");
                    break;
                case 6:
                    bulan.setText("Juli");
                    break;
                case 7:
                    bulan.setText("Agustus");
                    break;
                case 8:
                    bulan.setText("September");
                    break;
                case 9:
                    bulan.setText("October");
                    break;
                case 10:
                    bulan.setText("November");
                    break;
                case 11:
                    bulan.setText("December");
                    break;
            }
        });
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),android.R.style.Theme_Holo_Dialog, dateSetListener, currentdate.get(Calendar.YEAR), currentdate.get(Calendar.MONTH), currentdate.get(Calendar.DAY_OF_MONTH));
        ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("year", "id", "android")).setVisibility(View.GONE);
        ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        datePickerDialog.show();
    }

    private void showdatedaypicker() {
        final Calendar currentdate = Calendar.getInstance();
        date = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            Log.d("asda", "showdateyearpicker: " + year);
            hari.setText(String.valueOf(day));
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),android.R.style.Theme_Holo_Dialog, dateSetListener, currentdate.get(Calendar.YEAR), currentdate.get(Calendar.MONTH), currentdate.get(Calendar.DAY_OF_MONTH));
        ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("year", "id", "android")).setVisibility(View.GONE);
        ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("month", "id", "android")).setVisibility(View.GONE);
        datePickerDialog.show();
    }

    private void showdateyearpicker() {
        final Calendar currentdate = Calendar.getInstance();
        date = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            Log.d("asda", "showdateyearpicker: " + year);
            tahun.setText(String.valueOf(year));
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),android.R.style.Theme_Holo_Dialog, dateSetListener, currentdate.get(Calendar.YEAR), currentdate.get(Calendar.MONTH), currentdate.get(Calendar.DAY_OF_MONTH));
        ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("month", "id", "android")).setVisibility(View.GONE);
        datePickerDialog.show();
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
