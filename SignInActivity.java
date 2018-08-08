package com.example.root.gogetitserver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.root.gogetitserver.common.Common;
import com.example.root.gogetitserver.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import info.hoang8f.widget.FButton;

public class SignInActivity extends AppCompatActivity {

    MaterialEditText phone_no,password;
    FButton signInBtn;

    DatabaseReference users;
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        phone_no = findViewById(R.id.edtPhone);
        password = findViewById(R.id.edtPassword);

        signInBtn = findViewById(R.id.btn_signIn_activity);

        //init Firebase
        db = FirebaseDatabase.getInstance();
         users = db.getReference("User");

         signInBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 signInUser(phone_no.getText().toString(),password.getText().toString());
             }
         });
    }

    private void signInUser(final String phone, String password) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        final String local_phone = phone;
        final String local_password = password;
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(local_phone).exists()){
                    progressDialog.dismiss();

                    User user = dataSnapshot.child(local_phone).getValue(User.class);
                    user.setPhone(local_phone);

                    if (Boolean.parseBoolean(user.getIsStaff())){
                        if (user.getPassword().equals(local_password)){
                            //signIn  Login OK
                            Intent intent = new Intent(SignInActivity.this,HomeActivity.class);
                            Common.current_user = user;
                            startActivity(intent);
                            finish();
                            

                        }else {
                            Toast.makeText(SignInActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                    }else
                        Toast.makeText(SignInActivity.this, "Please Login with Staff account", Toast.LENGTH_SHORT).show();

                }else {
                    progressDialog.dismiss();
                    Toast.makeText(SignInActivity.this, "Phone Number not registered", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
