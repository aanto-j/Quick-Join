package com.aanto.quickjoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminLoginActivity extends AppCompatActivity {
    EditText AdminName,AdminPass;
    Button Login;
    DatabaseReference reference;
    String name,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        AdminName = findViewById(R.id.admin_name);
        AdminPass = findViewById(R.id.admin_password);
        Login = findViewById(R.id.submit_cred);
        reference = FirebaseDatabase.getInstance().getReference().child(Constants.Admin);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = AdminName.getText().toString().trim();
                pass = AdminPass.getText().toString().trim();
                if(snapshot.child("admin_name").child(name).exists()){
                    if(snapshot.child("admin_pass").child(name).exists()){
                        Login.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(AdminLoginActivity.this,HomeActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                }
                else{
                    Login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(AdminLoginActivity.this, "Admin Does Not Exist", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}