package com.example.heyshan.worknhire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }
    public void toLogin(View view) {

        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        //finish();
    }

    public void toSignIn(View view) {

        Intent intent = new Intent(MainActivity.this, User.class);
        startActivity(intent);
        //finish();
    }

}
