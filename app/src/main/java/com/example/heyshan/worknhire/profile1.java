package com.example.heyshan.worknhire;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
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
import com.example.heyshan.worknhire.Chat.ChatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class profile1 extends AppCompatActivity {
    public String empName, empEmail, empPhoneNo, empworkType, workerId,workerAvailable;
    public Button btnChat, Rate, btnBook,button2;
    public RatingBar ratingBar;
    public String mSenderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);
        empName = getIntent().getStringExtra("firstname") + " " + getIntent().getStringExtra("lastname");
        empEmail = getIntent().getStringExtra("email");
        empPhoneNo = getIntent().getStringExtra("phoneno");
        empworkType = getIntent().getStringExtra("worktype");
        workerAvailable=getIntent().getStringExtra("workerAvailable");
        workerId = getIntent().getStringExtra("workerId");
        SharedPreferences sharedPref = getSharedPreferences(
                "spName", Context.MODE_PRIVATE);
        mSenderId = sharedPref.getString("id", "");

        //Rate = findViewById(R.id.button4);

        ratingBar = findViewById(R.id.rating);



        Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int noofstars = ratingBar.getNumStars();
                float getrating = ratingBar.getRating();
                AddRate(mSenderId,"5");
                Toast.makeText(profile1.this, mSenderId + getrating, Toast.LENGTH_SHORT).show();
            }
        });

//
        TextView displayName = findViewById(R.id.fullnametxt1);
        displayName.setText(empName);
        TextView displayEmail = findViewById(R.id.emailtxt1);
        displayEmail.setText(empEmail);
        TextView displayContact = findViewById(R.id.telephonetxt1);
        displayContact.setText(empPhoneNo);
        TextView displayWorktype = findViewById(R.id.worktypetxt1);
        displayWorktype.setText(empworkType);

        btnBook=findViewById(R.id.btnBookMe);
        if(workerAvailable.equals("NotAvailable")){
            btnBook.setVisibility(View.GONE);
        }
        if(workerAvailable.equals("Available")){
            btnBook.setVisibility(View.VISIBLE);
        }

        btnChat = findViewById(R.id.btnCreateChat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile1.this, ChatActivity.class);
                intent.putExtra("receiverId", workerId);
                intent.putExtra("receiverName", empName);
                startActivity(intent);
            }
        });
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile1.this,Login.class);
                startActivity(intent);
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAvailability();
                btnBook.setVisibility(View.GONE);

            }
        });




    }
   public void changeAvailability(){
      Volleypostfunc("http://138.68.177.4:3000/api/empl/"+workerId);
       Toast.makeText(profile1.this, "You Booked "+empName, Toast.LENGTH_LONG).show();

    }
    public void  Volleypostfunc(String Posturl)
    {





        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();


            jsonBody.put("availability","NotAvailable") ;






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

    private void AddRate(String userID, String rate) {
        try {

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("userId", userID);
            jsonBody.put("rating", rate);


            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, "http://138.68.177.4:3000/api/addRating", jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {



                    Toast.makeText(getApplicationContext(), "Rating Creation Succesful ", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(), "Response:  " + error.toString(), Toast.LENGTH_LONG).show();


                }
            }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        final Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Basic " + "c2FnYXJAa2FydHBheS5jb206cnMwM2UxQUp5RnQzNkQ5NDBxbjNmUDgzNVE3STAyNzI=");//put your token here
                        return headers;
                    }
            };
            Volley.newRequestQueue(getApplicationContext()).add(jsonOblect);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
