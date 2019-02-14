package com.example.heyshan.worknhire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ForgetPassword extends AppCompatActivity {

    public void toChangePassword(View view) {

        Intent intent = new Intent(ForgetPassword.this, ChangePassword.class);
        startActivity(intent);
        //finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }
}
