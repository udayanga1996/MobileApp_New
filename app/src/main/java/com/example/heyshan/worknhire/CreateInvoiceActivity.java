package com.example.heyshan.worknhire;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.AuthFailureError;
import java.util.*;

import org.json.JSONObject;

public class CreateInvoiceActivity extends AppCompatActivity {

     private EditText JobCost,ServiceChrge,TotalCost;
     private Button CreateInvoice1;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);
        mQueue = Volley.newRequestQueue(this);

        JobCost = findViewById(R.id.jobcost);
        ServiceChrge = findViewById(R.id.servicecharge);
        TotalCost = findViewById(R.id.total);
        CreateInvoice1 = findViewById(R.id.button);

        CreateInvoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(JobCost.getText().toString())&&TextUtils.isEmpty(ServiceChrge.getText().toString())&& TextUtils.isEmpty(TotalCost.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Please Fill Details ", Toast.LENGTH_SHORT).show();
                }else{
                    passInvoice(JobCost.getText().toString(),ServiceChrge.getText().toString(),TotalCost.getText().toString());

                }
            }
        });
    }

    private void passInvoice(String jobCost,String ServiceChr , String Total) {
        try {

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Basic_charge", jobCost);
            jsonBody.put("Cost", ServiceChr);
            jsonBody.put("Total_Cost", Total);


            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, "http://138.68.177.4:3000/api/createinvoice", jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(getApplicationContext(), "Invoice Creation Succesful " , Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(), "Error:  " + error.toString(), Toast.LENGTH_LONG).show();


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

