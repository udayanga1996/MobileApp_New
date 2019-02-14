package com.example.heyshan.worknhire.Chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.heyshan.worknhire.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private String mReceiverId;
    private String mSenderId;
    private String mReceiverName;
    private String mSenderName;
    private String mSentImage;
    private ImageButton mImageBtnSend;
    private EditText mEtMessage;
    private DatabaseReference mRootRef;

    private RecyclerView mRecyclerView;
    private List<MessageItem> mMessageItemsList;
    private RecyclerView.Adapter mMessageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        mReceiverId = intent.getStringExtra("receiverId");
        mReceiverName = intent.getStringExtra("receiverName");
        SharedPreferences sharedPref = getSharedPreferences(
                "spName", Context.MODE_PRIVATE);
        mSenderId = sharedPref.getString("id", "");
        mSenderName = sharedPref.getString("senderName", "");

        mRootRef = FirebaseDatabase.getInstance().getReference();

        mImageBtnSend = findViewById(R.id.imageButtonSend);
        mEtMessage =  findViewById(R.id.etMessage);
        mImageBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        mRecyclerView = findViewById(R.id.RecyclerViewMessages);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessageItemsList = new ArrayList<>();
        mMessageAdapter = new MessagesAdapter(mMessageItemsList, this, mReceiverName);
        mRecyclerView.setAdapter(mMessageAdapter);

        loadMessages();

    }

    public void sendMessage(){
        mImageBtnSend.setClickable(false);
        mImageBtnSend.setBackgroundColor(Color.parseColor("#7f060026"));
        String message = mEtMessage.getText().toString();
        if(!TextUtils.isEmpty(message)){
            String senderRef = "messages/" + mSenderId + "/" + mReceiverId;
            String receiverRef = "messages/" + mReceiverId + "/" + mSenderId;
            String empListref = "empList/" + mReceiverId + "/" + mSenderId;

            String pushIdSender = mRootRef.child("messages").child(mSenderId).child(mReceiverId)
                    .push().getKey();

            String pushIdReceiver = mRootRef.child("messages").child(mReceiverId).child(mSenderId)
                    .push().getKey();

            Map messageMap = new HashMap();
            messageMap.put("SentImage",mSentImage);
            messageMap.put("body",message);
            messageMap.put("seen",false);
            messageMap.put("timestamp", ServerValue.TIMESTAMP);
            messageMap.put("sentBy", mSenderId);
            messageMap.put("sentName", mSenderName);
            messageMap.put("senderMsgKey", pushIdReceiver);

            Map messageMapReceiver = new HashMap();
            messageMapReceiver.put("SentImage",mSentImage);
            messageMapReceiver.put("body",message);
            messageMapReceiver.put("seen",false);
            messageMapReceiver.put("timestamp", ServerValue.TIMESTAMP);
            messageMapReceiver.put("sentBy", mSenderId);
            messageMapReceiver.put("sentName", mSenderName);
            messageMapReceiver.put("senderMsgKey", pushIdSender);

            Map empList = new HashMap();
            empList.put("userId", mSenderId);
            empList.put("name", mSenderName);

            Map messageUserMap = new HashMap();
            messageUserMap.put(senderRef + "/" + pushIdSender, messageMap);
            messageUserMap.put(receiverRef + "/" + pushIdReceiver, messageMapReceiver);
            messageUserMap.put(empListref, empList);

            mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if(databaseError != null){
                        Toast.makeText(getApplicationContext(),databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }else{
                        mEtMessage.setText("");
                    }
                    mImageBtnSend.setClickable(true);
                    mImageBtnSend.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            });
        }
    }

    private void loadMessages() {
        mRootRef.child("messages").child(mSenderId).child(mReceiverId)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Log.d("ABC", "onChildAdded: " + dataSnapshot.toString());
                        MessageItem message = dataSnapshot.getValue(MessageItem.class);
                        mMessageItemsList.add(message);
                        mMessageAdapter.notifyDataSetChanged();
                        mRecyclerView.scrollToPosition(mMessageAdapter.getItemCount()-1);

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
