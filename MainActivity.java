package com.example.root.gogetitserver;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity {

    FButton  btnSignIn;

    TextView txtSlogan, txtlogo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnSignIn = findViewById(R.id.btn_signIn);


        txtSlogan = findViewById(R.id.textSlogan);
        txtlogo = findViewById(R.id.textLogo);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Pacifico.ttf");
        txtSlogan.setTypeface(typeface);
        txtlogo.setTypeface(typeface);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}
