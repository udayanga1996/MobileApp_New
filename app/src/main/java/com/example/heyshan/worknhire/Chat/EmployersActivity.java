package com.example.heyshan.worknhire.Chat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.heyshan.worknhire.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EmployersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter employersAdapter;
    private List<EmployerItem> employersList;
    private ProgressDialog mProgressDialog;
    private DatabaseReference mRootRef;
    private String mSenderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);

        mProgressDialog = new ProgressDialog(EmployersActivity.this);
        mProgressDialog.setTitle("Fetching Data...");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        mRootRef = FirebaseDatabase.getInstance().getReference();
        SharedPreferences sharedPref = getSharedPreferences(
                "spName", Context.MODE_PRIVATE);
        mSenderId = sharedPref.getString("id", "");
        employersList = new ArrayList<>();

        loadEmployerList();

        recyclerView = findViewById(R.id.rvChatList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(EmployersActivity.this));
    }

    private void loadEmployerList() {
        mRootRef.child("empList").child(mSenderId)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        EmployerItem employee = dataSnapshot.getValue(EmployerItem.class);
                        employersList.add(employee);
                        employersAdapter = new EmployersAdapter(employersList, EmployersActivity.this);
                        recyclerView.setAdapter(employersAdapter);
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
