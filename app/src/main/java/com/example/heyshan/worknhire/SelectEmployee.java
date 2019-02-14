package com.example.heyshan.worknhire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectEmployee extends AppCompatActivity implements ExampleAdapter.OnItemClickListener{
    public String empType;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;
    String workerFName;
    String workerLName;
    String workerMobile;
    String workerType;
    String workerEmail;
    String workerId;
    String workerAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_employee);
        empType=getIntent().getStringExtra("apiurl");

        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList=new ArrayList<>();

        mRequestQueue= Volley.newRequestQueue(this);
        parseJSON(empType);
    }

    private void parseJSON(String urlforemp){
        String urlemp="http://138.68.177.4:3000/api/emp/"+urlforemp;

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, urlemp, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("employee");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject emp=jsonArray.getJSONObject(i);

                               // Toast.makeText(SelectEmployee.this, emp.toString(), Toast.LENGTH_LONG).show();
                                 workerFName=emp.getString("fname");
                                 workerLName=emp.getString("lname");
                                 workerMobile=emp.getString("mobileno");
                                 workerType=emp.getString("worktype");
                                 workerEmail=emp.getString("email");
                                 workerId = emp.getString("_id");
                                workerAvailability=emp.getString("availability");

                                mExampleList.add(new ExampleItem(workerFName,workerEmail,workerAvailability));
                            }
                            mExampleAdapter=new ExampleAdapter(SelectEmployee.this,mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnItemClickListener(SelectEmployee.this);


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
        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {

        Intent profileIntent=new Intent(this,profile1.class);
        ExampleItem clickedItem=mExampleList.get(position);
        profileIntent.putExtra("firstname",workerFName);
        profileIntent.putExtra("lastname",workerLName);
        profileIntent.putExtra("email",workerEmail);
        profileIntent.putExtra("phoneno",workerMobile);
        profileIntent.putExtra("worktype", workerType);
        profileIntent.putExtra("workerId", workerId);
        profileIntent.putExtra("workerAvailable", workerAvailability);
        startActivity(profileIntent);
    }
}
