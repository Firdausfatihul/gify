package app.gify.co.id.publicload.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import app.gify.co.id.R;
import app.gify.co.id.activity.Pembelian;
import app.gify.co.id.activity.Pengaturan;


public class SlideshowFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intent intent = new Intent(getContext(), Pembelian.class);
        startActivity(intent);
        getActivity().finish();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
