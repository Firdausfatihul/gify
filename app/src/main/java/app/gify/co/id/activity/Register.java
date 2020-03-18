package app.gify.co.id.activity;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.gify.co.id.R;

public class Register extends AppCompatActivity {

    EditText Nama, Email, NoHp, Password;
    TextView TanggalLahir;
    String nama, email, noHp, password, currentUserID;
    Button Masuk;

    Calendar date;
    StringBuilder selectedDate;

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

        TanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });

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
        NoHp = findViewById(R.id.noHPRegister);
        Password = findViewById(R.id.passwordRegister);
        TanggalLahir = findViewById(R.id.tanggalLahirRegister);
    }

    private void CreateNewAccount() {
        nama = Nama.getText().toString().trim();
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        noHp = NoHp.getText().toString().trim();

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
                                currentUserID = mAuth.getCurrentUser().getUid();
                                RootRef.child("Users").child(currentUserID).child("nama").setValue(nama);
                                RootRef.child("Users").child(currentUserID).child("email").setValue(email);
                                RootRef.child("Users").child(currentUserID).child("password").setValue(password);
                                RootRef.child("Users").child(currentUserID).child("tanggal").setValue(selectedDate.toString());
                                RootRef.child("Users").child(currentUserID).child("noHp").setValue(noHp);

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
        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Lnama = String.valueOf(dataSnapshot.child("nama").getValue());
                String Lemail = String.valueOf(dataSnapshot.child("email").getValue());
                String LnoHp = String.valueOf(dataSnapshot.child("noHp").getValue());
                String Lpassword = String.valueOf(dataSnapshot.child("password").getValue());
                String Ltanggal = String.valueOf(dataSnapshot.child("tanggal").getValue());
                String LID = dataSnapshot.getKey();

               Log.d("cobalah ",LID + " " + "Nama: " + Lnama + " " + "Email: " + Lemail + " " + "noHp: " + LnoHp + " " + "Password: " + Lpassword + " " + "Tanggal: " + Ltanggal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Intent loginIntent = new Intent(getApplication(), Login.class);
        startActivity(loginIntent);
        finish();
    }

    private void showDateTimePicker() {
        final Calendar currentdate = Calendar.getInstance();
        date = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                selectedDate = new StringBuilder().append(day)
                        .append("-").append(month + 1).append("-").append(year)
                        .append("");
                SimpleDateFormat format = new SimpleDateFormat("EEEE, dd - MMMM - yyyy");
                SimpleDateFormat currentDateformat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date dates = currentDateformat.parse(String.valueOf(selectedDate));
                    String tanggal = format.format(dates);
                    TanggalLahir.setText(tanggal);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, currentdate.get(Calendar.YEAR), currentdate.get(Calendar.MONTH), currentdate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
}
