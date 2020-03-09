package app.gify.co.id.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.gify.co.id.R;

public class Register extends AppCompatActivity {

    EditText Nama, Email, NoHp, Password;
    TextView TanggalLahir;
    String nama, email, noHp, password, tanggallahir;
    Button Masuk;

    FirebaseAuth mAuth;
    DatabaseReference RootRef;

    ProgressDialog loadingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        InitializeFields();

        Masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });
    }

    private void InitializeFields() {
        Masuk = findViewById(R.id.masukRegister);
        Nama = findViewById(R.id.namaRegister);
        Email = findViewById(R.id.emailLogin);
        Password = findViewById(R.id.passwordRegister);
        TanggalLahir = findViewById(R.id.tanggalLahirRegister);
    }

    private void CreateNewAccount() {
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(Register.this, "Please enter a Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(Register.this, "Please enter a Password", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar = new ProgressDialog(Register.this);
            loadingBar.setTitle("Membuat akun baru...");
            loadingBar.setMessage("Harap tunggu, Kami sedang membuat akun barumu...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String currentUserID = mAuth.getCurrentUser().getUid();
                                RootRef.child("Users").child(currentUserID).setValue("");

                                SendUserToMainActivity();
                                Toast.makeText(Register.this, "Selamat! akunmu berhasil dibuat", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else {
                                String message = task.getException().toString();
                                Toast.makeText(Register.this, "Gagal Register", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void SendUserToMainActivity() {
        Intent loginIntent = new Intent(getApplication(), Login.class);
        startActivity(loginIntent);
        finish();
    }
}
