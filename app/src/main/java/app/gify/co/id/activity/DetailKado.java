package app.gify.co.id.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.synnapps.carouselview.CarouselView;

import app.gify.co.id.R;

public class DetailKado extends AppCompatActivity {

    CarouselView slide;
    TextView nama, harga, desc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_barang);

        slide = findViewById(R.id.carousel);
        nama = findViewById(R.id.namadetail);
        harga = findViewById(R.id.hargadetail);
        desc = findViewById(R.id.descdetail);

        int hargas;
        hargas = getIntent().getIntExtra("harga", -1);

        nama.setText(getIntent().getStringExtra("nama"));
        harga.setText("Rp. " + hargas);
        desc.setText(getIntent().getStringExtra("desc"));

    }
}
