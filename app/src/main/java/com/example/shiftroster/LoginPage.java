package com.example.shiftroster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    Button btn_login;
    ImageView show_pass;
    EditText email, password;
    Boolean eyeflag= false;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!= null)
        {
            startActivity(new Intent(LoginPage.this, AdminActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        btn_login= findViewById(R.id.btn_login);
        email = findViewById(R.id.email_main);
        password= findViewById(R.id.pass_main);
        show_pass= findViewById(R.id.show_pass);

        firebaseAuth = FirebaseAuth.getInstance();

        show_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!eyeflag){
                    show_pass.setImageResource(R.drawable.eyeicon);
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    eyeflag= true;
                }else{
                    show_pass.setImageResource(R.drawable.eyeclosed);
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eyeflag= false;
                }

            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingBar loadingBar = new LoadingBar(LoginPage.this);
                loadingBar.showAlertDialog();
                if(TextUtils.isEmpty(email.getText().toString()) ||TextUtils.isEmpty(password.getText().toString())){
                    loadingBar.dismissAlertDialog();
                    Toast.makeText(LoginPage.this, "Please enter credentials", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
                    email.setError("Please enter Valid email address");
                    loadingBar.dismissAlertDialog();
                }else{

                    String mail = email.getText().toString().trim();
                    String pass =password.getText().toString().trim();
                    firebaseAuth.signInWithEmailAndPassword(mail, pass)
                            .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        loadingBar.dismissAlertDialog();
                                        startActivity(new Intent(LoginPage.this, AdminActivity.class));
                                    } else {
                                        loadingBar.dismissAlertDialog();
                                        Toast.makeText(LoginPage.this, "Error ! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });}
            }
        });
    }
}