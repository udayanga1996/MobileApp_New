package com.example.heyshan.worknhire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignInUser extends AppCompatActivity {

    private Button btnSignUpUse;
    private EditText Fname, Lname, Email, MobileNum, Password,Password2;
    private ProgressDialog pDialog;
    private TextView mResult;
    String Posturl="http://138.68.177.4:3000/api/clientRegister";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_user);


        Fname =  findViewById(R.id.etFirstNameInSignInUse);
        Lname =  findViewById(R.id.etLastNameSignInUse);
        Email =  findViewById(R.id.etEmailInSignInUse);
        MobileNum =  findViewById(R.id.etMobileNumInSignInUse);
        Password = findViewById(R.id.etPasswordInSignInUse);
        Password2 = findViewById(R.id.etPasswordInSignInUse2);
        btnSignUpUse =  findViewById(R.id.btnSignUpUse);

        btnSignUpUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
                if (validation()) {
                    registerUser();
                } else {
                    Toast.makeText(SignInUser.this, "Please enter valid details", Toast.LENGTH_LONG).show();
                }

            }
        });



    }
    private boolean validation(){
        boolean valid =true;
        if(Fname.getText().toString().length()<1||Fname.getText().toString().length()>32) {
            Fname.setError("Please enter valid name");
            valid = false;
        }
        if(Lname.getText().toString().length()<1||Lname.getText().toString().length()>32) {
            Lname.setError("Please enter valid name");
            valid = false;
        }
        if (Email.getText().toString().length() < 1 || !Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()) {
            Email.setError("Please enter valid email address");
            valid = false;
        }
        if (MobileNum.getText().toString().length() < 1 || !Patterns.PHONE.matcher(MobileNum.getText().toString()).matches()) {
            MobileNum.setError("Please enter valid phone number");
            valid = false;
        }
        if (Password.getText().toString().length() < 1) {
            Password.setError("Please enter valid password");
            valid = false;
        }
        if (Password2.getText().toString().length() < 1) {
            Password2.setError("Please enter valid password");
            valid = false;
        }
        return valid;

    }

    private void registerUser(){

        Volleypostfunc(Posturl);
        Toast.makeText(SignInUser.this, "Successfuly registered", Toast.LENGTH_LONG).show();
        Intent loginIntent=new Intent(SignInUser.this,Login.class);
        startActivity(loginIntent);
        finish();


    }
    public void  Volleypostfunc(String Posturl)
    {

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("fname", Fname.getText().toString());
            jsonBody.put("lname", Lname.getText().toString());
            jsonBody.put("email", Email.getText().toString());
            jsonBody.put("mobileno", MobileNum.getText().toString());
            jsonBody.put("password", Password.getText().toString());
            jsonBody.put("password2", Password.getText().toString());

            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Posturl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }


                public byte[] getBody () throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");

                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupperted Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                protected Response<String> parseNetworkResponse (NetworkResponse response){
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




}
