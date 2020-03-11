package app.gify.co.id.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.gify.co.id.R;
import app.gify.co.id.sessions.SessionManager;

public class Login extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText Email, Password;
    String email, password, userID;
    Button Masuk, Daftar;
    ProgressDialog progressBar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SessionManager sessionManager;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

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
            finish();
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
                progressBar.show();
                //wajib di tambahkan untuk menghindari null
                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this, "Isi yang kosong terlebih dahulu", Toast.LENGTH_SHORT).show();
                    progressBar.dismiss();
                    Masuk.setVisibility(View.VISIBLE);
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
                                        Toast.makeText(Login.this, "Email / Sandi salah " , Toast.LENGTH_SHORT).show();
                                        progressBar.dismiss();
                                        Masuk.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                }
            }
        });
    }

    private void SendUserToMainActivity() {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();
        String user = mAuth.getCurrentUser().getUid();

        //First Approach
        mDb.child("Users").child(user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userID = String.valueOf(dataSnapshot.child("nama").getValue());
                Log.d("namaku", "onDataChange: " + userID);
                Intent mainIntent = new Intent(getApplication(), MainActivity.class);
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                sessionManager.checkLogin(true);
                editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.putString("nama", userID);
                editor.apply();
                startActivity(mainIntent);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
