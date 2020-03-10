package app.gify.co.id.publicload.ui.home;

import android.content.DialogInterface;
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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.kadolist, R.layout.spinnner);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        kadobuatsiapa.setAdapter(adapter);
        kadobuatsiapa.setOnItemSelectedListener(HomeFragment.this);

        ArrayAdapter<CharSequence> adapteracara = ArrayAdapter.createFromResource(getContext(), R.array.acaralist, R.layout.spinnner);
        adapteracara.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        acarapa.setAdapter(adapteracara);
        acarapa.setOnItemSelectedListener(HomeFragment.this);

        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
