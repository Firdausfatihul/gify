package app.gify.co.id.publicload.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import app.gify.co.id.R;


public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private HomeViewModel homeViewModel;
    TextView hari, bulan, tahun;
    String kadobuatsiapaku, acaraapaku, hariku, bulanku, tahunku;
    NumberPicker numberpicker;
    Spinner kadobuatsiapa, acarapa;
    String[] kadolist;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel=ViewModelProviders.of(this).get(HomeViewModel.class);
        View root=inflater.inflate(R.layout.fragment_home, container, false);
        kadobuatsiapa=root.findViewById(R.id.buatSiapaCari);
        acarapa=root.findViewById(R.id.acaraApaCari);
        hari=root.findViewById(R.id.tanggalCari);
        bulan=root.findViewById(R.id.bulanCari);
        tahun=root.findViewById(R.id.tahunCari);

        loadSpinner();

        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void loadSpinner(){

        HintArrayAdapter hintAdapter = new HintArrayAdapter<String>(getContext(), 0);

        HintArrayAdapter hintadapterku = new HintArrayAdapter<String>(getContext(), 0);

        hintAdapter.add("Hint to be displayed");
        hintAdapter.add("Item 1");
        hintAdapter.add("Item 2");
        hintAdapter.add("Item 30");

        hintadapterku.add("asad");
        hintadapterku.add("tes");
        kadobuatsiapa.setAdapter(hintAdapter);

        //spinner1.setSelection(0); //display hint. Actually you can ignore it, because the default is already 0
        kadobuatsiapa.setSelection(0, false); //use this if don't want to onItemClick called for the hint

        kadobuatsiapa.setOnItemSelectedListener(HomeFragment.this);

        acarapa.setAdapter(hintadapterku);

        acarapa.setAdapter(hintadapterku);

        acarapa.setAdapter(hintadapterku);
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
}
