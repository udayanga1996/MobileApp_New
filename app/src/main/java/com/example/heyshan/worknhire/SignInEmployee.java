package com.example.heyshan.worknhire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.android.volley.toolbox.JsonObjectRequest;
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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;
import static com.android.volley.Request.Method.POST;

public class SignInEmployee extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnSignUpEmp;
    private EditText Fname, Lname, Email, MobileNum, Password, Password2;
    private ProgressDialog pDialog;
    private TextView mResult;
    String selectedWorkType;

    String Posturl = "http://138.68.177.4:3000/api/employeeRegister";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_employee);

        Spinner spinner = findViewById(R.id.spinner1);
        Fname = findViewById(R.id.etFirstNameInSignInEmp);
        Lname = findViewById(R.id.etLastNameSignInEmp);
        Email = findViewById(R.id.etEmailInSignInEmp);
        MobileNum = findViewById(R.id.etMobileNumInSignInEmp);
        Password = findViewById(R.id.etPasswordInSignInEmp);
        Password2 = findViewById(R.id.etPasswordInSignInEmp2);
        btnSignUpEmp = findViewById(R.id.btnSignUpEmp);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.position, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btnSignUpEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                if (validate()) {
                    registerUser();
                } else {
                    Toast.makeText(SignInEmployee.this, "Please enter valid details", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    public boolean validate() {
        boolean valid = true;
        if (Fname.getText().toString().length() < 1 || Fname.getText().toString().length() > 32) {
            Fname.setError("Please enter valid name");
            valid = false;
        }
        if (Lname.getText().toString().length() < 1 || Lname.getText().toString().length() > 32) {
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
       // if (!Password2.getText().toString().equals(Password)) {
         //   Password.setError("Passwords don't match!");
           // valid = false;
        //}

        return valid;
    }


    public void registerUser() {

        Volleypostfunc(Posturl);
        Toast.makeText(SignInEmployee.this, "Successfuly registered", Toast.LENGTH_LONG).show();

        Intent loginIntent = new Intent(SignInEmployee.this, Login.class);
        startActivity(loginIntent);
        finish();


    }


    public void Volleypostfunc(String Posturl) {


        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();

            jsonBody.put("fname", Fname.getText().toString());
            jsonBody.put("lname", Lname.getText().toString());
            jsonBody.put("email", Email.getText().toString());
            jsonBody.put("mobileno", MobileNum.getText().toString());
            jsonBody.put("password", Password.getText().toString());
            jsonBody.put("password2", Password.getText().toString());
            jsonBody.put("worktype", selectedWorkType);
            jsonBody.put("availability", "Available");

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


                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");

                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupperted Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                protected Response<String> parseNetworkResponse(NetworkResponse response) {
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedWorkType = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), selectedWorkType, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}

