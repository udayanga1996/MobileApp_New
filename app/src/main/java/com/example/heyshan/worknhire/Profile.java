package com.example.heyshan.worknhire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.heyshan.worknhire.Chat.EmployersActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import io.reactivex.annotations.NonNull;

public class Profile extends AppCompatActivity {
    public String empName,empEmail,empPhoneNo,empworkType,empAvailability,workerId;
    private  Button  createInvoice;
    private Button chatWithEmployers;
    public Button btnLogOut;
    public Button btnEndContract1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        empName=getIntent().getStringExtra("firstname")+" "+getIntent().getStringExtra("lastname");
        empEmail=getIntent().getStringExtra("email");
        workerId=getIntent().getStringExtra("empid");
        empPhoneNo=getIntent().getStringExtra("phoneno");
        empworkType=getIntent().getStringExtra("worktype");
        empAvailability=getIntent().getStringExtra("availability");


        createInvoice = findViewById(R.id.btnCreateInvoice);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnEndContract1=findViewById(R.id.btnEndContract);






        TextView displayName=findViewById(R.id.fullnametxt);
        displayName.setText(empName);
        TextView displayEmail=findViewById(R.id.emailtxt);
        displayEmail.setText(empEmail);
        TextView displayContact=findViewById(R.id.telephonetxt);
        displayContact.setText(empPhoneNo);
        TextView displayWorktype=findViewById(R.id.worktypetxt);
        displayWorktype.setText(empworkType);

        btnEndContract1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAvailability();
                btnEndContract1.setVisibility(View.VISIBLE);
            }
        });

        createInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),CreateInvoiceActivity.class);
                startActivity(i);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });


        chatWithEmployers = findViewById(R.id.btnChatWithEmployer);
        chatWithEmployers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, EmployersActivity.class);
                startActivity(intent);
            }
        });

        if(empAvailability.equals("NotAvailable")){
            btnEndContract1.setVisibility(View.VISIBLE);
        }
        if(empAvailability.equals("Available")){
            btnEndContract1.setVisibility(View.VISIBLE);
        }

    }

    public void changeAvailability(){
        Volleypostfunc("http://138.68.177.4:3000/api/empl/"+workerId);

        Toast.makeText(Profile.this, "You End the contract", Toast.LENGTH_LONG).show();

    }
    public void  Volleypostfunc(String Posturl)
    {





        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();


            jsonBody.put("availability","Available") ;






            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, Posturl, new Response.Listener<String>() {
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
