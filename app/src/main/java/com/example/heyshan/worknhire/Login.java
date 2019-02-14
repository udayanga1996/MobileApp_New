package com.example.heyshan.worknhire;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;
import static com.android.volley.Request.Method.POST;

public class Login extends AppCompatActivity {
    private RequestQueue mQueue;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnDisplay;
    private Button btnLogIn;
    private EditText EmailFromForm;
    private EditText passwordFromForm;
    private ProgressDialog pDialog;


    String emailtxtbox;
    String passwordtxtbox;
    String resEmail;
    String resPass;
    String ressId;
    String resfirstname;
    String reslastname;
    String resContact;
    String resWorktype;
    String resWorkAvailability;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mQueue=Volley.newRequestQueue(this);

        EmailFromForm = findViewById(R.id.etEmailInLogin);
        passwordFromForm = findViewById(R.id.etPasswordInLogin);
        btnLogIn = findViewById(R.id.btnLogIn);
        radioGroup = (RadioGroup) findViewById(R.id.radio);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailtxtbox=EmailFromForm.getText().toString();
                passwordtxtbox=passwordFromForm.getText().toString();


                    if(android.util.Patterns.EMAIL_ADDRESS.matcher(emailtxtbox).matches()) {
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);

                        if (radioButton.getText().toString().equals("Client")) {
                            Toast.makeText(Login.this,
                                    emailtxtbox, Toast.LENGTH_SHORT).show();
                            loginPass("http://138.68.177.4:3000/api/client/" + emailtxtbox, "Client");

                        } else if (radioButton.getText().toString().equals("Employee")) {

                            loginPass("http://138.68.177.4:3000/api/employees/" + emailtxtbox, "Employee");
                        }
                    }
                    else{
                        Toast.makeText(Login.this, "Please enter vaild Email ", Toast.LENGTH_LONG).show();
                    }
           }
        });





    }
    private void loginPass(String particularUrl,String Type){
        String loginurl=particularUrl;
        final String personType=Type;

        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, loginurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {



                           //Toast.makeText(Login.this,
                                // response.toString(), Toast.LENGTH_SHORT).show();
                                JSONObject student=response;


                            if(personType.equals("Client")){
                                resEmail=student.getString("email");
                                resPass=student.getString("password");
                                resfirstname=student.getString("fname");
                                reslastname=student.getString("lname");
                                resContact=student.getString("mobileno");
                                SharedPreferences sharedPref = getSharedPreferences(
                                        "spName", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("id", student.getString("_id"));
                                editor.putString("senderName", resfirstname);
                               // Toast.makeText(Login.this, student.getString("_id"), Toast.LENGTH_SHORT).show();
                                editor.commit();
                            }

                            if(personType.equals("Employee")) {

                                ressId=student.getString("_id");
                                resEmail = student.getString("email");
                                resPass = student.getString("password");
                                resfirstname = student.getString("fname");
                                reslastname = student.getString("lname");
                                resContact = student.getString("mobileno");
                                resWorktype = student.getString("worktype");
                                resWorkAvailability=student.getString("availability");

                                SharedPreferences sharedPref = getSharedPreferences(
                                        "spName", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("id", student.getString("_id"));
                                editor.putString("senderName", resfirstname);
                               // Toast.makeText(Login.this, student.getString("_id"), Toast.LENGTH_SHORT).show();
                                editor.commit();
                            }




                            if(resEmail.equals(emailtxtbox)&&resPass.equals(passwordtxtbox)){


                                Toast.makeText(Login.this, "Successfuly Login", Toast.LENGTH_LONG).show();
                                if(personType.equals("Client")){
                                    Intent JobIntent=new Intent(Login.this,FindJob.class);
                                    startActivity(JobIntent);

                                }
                                if(personType.equals("Employee")){
                                    Intent profileIntent=new Intent(Login.this,Profile.class);
                                    profileIntent.putExtra("empid",ressId);
                                    profileIntent.putExtra("firstname",resfirstname);
                                    profileIntent.putExtra("lastname",reslastname);
                                    profileIntent.putExtra("email",resEmail);
                                    profileIntent.putExtra("phoneno",resContact);
                                    profileIntent.putExtra("worktype",resWorktype);
                                    profileIntent.putExtra("availability",resWorkAvailability);
                                    startActivity(profileIntent);

                                }
                            }
                            else{

                                Toast.makeText(Login.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }






}
