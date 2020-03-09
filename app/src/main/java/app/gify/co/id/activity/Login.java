package app.gify.co.id.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import app.gify.co.id.R;
import app.gify.co.id.sessions.SessionManager;

public class Login extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText Email, Password;
    String email, password;
    Button Masuk, Daftar;
    ProgressDialog progressBar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SessionManager sessionManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.emailLogin);
        Password = findViewById(R.id.passwordLogin);
        Masuk = findViewById(R.id.masuk);
        Daftar = findViewById(R.id.daftar);

        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplication(), Register.class);
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                startActivity(registerIntent);
            }
        });

        sessionManager = new SessionManager(Login.this);

        if (sessionManager.isLogged()) {
            Intent mainIntent = new Intent(getApplication(), MainActivity.class);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
            String emailKu = sharedPreferences.getString("email", "");
            Log.d("emailLoginSession", emailKu);
            startActivity(mainIntent);
        }

        Masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = Email.getText().toString().trim();
                password = Password.getText().toString().trim();
                Masuk.setVisibility(View.GONE);
                progressBar = new ProgressDialog(Login.this);
                progressBar.setTitle("Sign In");
                progressBar.setMessage("Harap Tunggu...");
                progressBar.setCanceledOnTouchOutside(false);
                progressBar.setCancelable(false);
                progressBar.show();
                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this, "Isi terlebih dulu yang kosong", Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                }else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Login.this, "Selamat datang!", Toast.LENGTH_SHORT).show();
                                        SendUserToMainActivity();
                                        progressBar.dismiss();
                                    }
                                    else {
                                        String message = task.getException().toString();
                                        Toast.makeText(Login.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                        progressBar.dismiss();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(getApplication(), MainActivity.class);
        sessionManager.checkLogin(true);
        editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();
        startActivity(mainIntent);
    }
}
