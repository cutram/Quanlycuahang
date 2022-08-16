package com.example.quanlybanhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
EditText edEmail, edPass;
Button btnDN,btnDK;
    int check;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        edEmail =findViewById(R.id.edEmail1);
        edPass = findViewById(R.id.edPassword1);
        btnDN = findViewById(R.id.btnLogin);
        btnDK= findViewById(R.id.btnTTK);
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String e = edEmail.getText().toString().trim();
              String p = edPass.getText().toString().trim();
              if (kiemTra(e,p)==1){
                  dangNhap(e,p);
              }
            }


        });
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = edEmail.getText().toString().trim();
                String p = edEmail.getText().toString().trim();
                if (kiemTra(e,p)==1){
                    dangKy(e,p);
                }
            }
        });
    }
    private int kiemTra(String email, String pass){
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            if (pass.length() < 6){
                Toast.makeText(this, "Mật khẩu phải ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                check = -1;
            }else check = 1;

        }
        return check;
    }
    private void dangNhap(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass).
                //thong bao khi hoan thanh
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,ProductActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
    private void dangKy(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass).
                //thong bao khi hoan thanh
                        addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}