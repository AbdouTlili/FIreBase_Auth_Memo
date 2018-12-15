package com.example.abdou.csemembersapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Add_event extends AppCompatActivity {

    FloatingActionButton addButt;
    FloatingActionButton calButt ;
    CalendarView eventDate ;
    Boolean calOpened;
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        addButt = (FloatingActionButton) findViewById(R.id.addButt);
        calButt = (FloatingActionButton) findViewById(R.id.calButt);
        eventDate = (CalendarView) findViewById(R.id.calendarView);
        calOpened=false;
        eventDate.setVisibility(View.GONE);
        userAuth = FirebaseAuth.getInstance();


        calButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calOpened){
                    calOpened=false;
                    eventDate.setVisibility(View.GONE);
                    //Toast.makeText(getApplicationContext(),"gone",Toast.LENGTH_SHORT).show();
                }else {
                    calOpened=true;
                    eventDate.setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(),"visible",Toast.LENGTH_SHORT).show();
                }
            }
        });


        addButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMemoFB();
            }
        });

    }



    void saveMemoFB(){

        // first section
        // get the data to save in the firebase
        EditText eventName = (EditText) findViewById(R.id.eventName);
        EditText eventComment = (EditText) findViewById(R.id.eventComment);


        CalendarView eventDate = (CalendarView) findViewById(R.id.calendarView);

        //Calendar c = Calendar.getInstance();

        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(eventDate.getDate());

        String dateString =ss.format(date);
        Toast.makeText(getApplicationContext(),dateString,Toast.LENGTH_SHORT).show();


        //make the modal object and convert it into hasmap


        //second section
        //save it to the firebase db
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(userAuth.getUid());

        /*
        myRef.child("name").setValue("eee");
        myRef.child("comment").setValue("eee");
        myRef.child("date").setValue("eee");

*/


        String key = database.getReference("memoList").push().getKey();

        Memo memo = new Memo();
        memo.setName(eventName.getText().toString());
        memo.setComment(eventComment.getText().toString());
        memo.setDate(dateString);
        myRef.setValue(memo);



        /*Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put( key, memo.toFirebaseObject());
        database.getReference("todoList").updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    finish();
                }*/
    }


}
