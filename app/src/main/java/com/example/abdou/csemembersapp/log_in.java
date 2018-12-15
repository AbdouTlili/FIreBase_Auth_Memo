package com.example.abdou.csemembersapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class log_in extends AppCompatActivity {

    private FirebaseAuth userAuth;
    Button logInButt ;
    EditText userMail;
    EditText userPasswd ;
    public boolean logedIn=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



        boolean connected = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        final boolean isConnected = connected;

        logInButt = (Button) findViewById(R.id.logInButt);
        userMail = (EditText) findViewById(R.id.userMail);
        userPasswd = (EditText) findViewById(R.id.userPasswd);
        try {
            logInButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logUser(isConnected);
                }
            });

        }
        catch (Exception e){Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();}
    }



    void logUser(boolean isConnected){
        if (isConnected) {
            String mail = userMail.getText().toString();
            String password = userPasswd.getText().toString();
            userAuth = FirebaseAuth.getInstance();
            userAuth.signInWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("logedin", "signInWithEmail:success");
                                FirebaseUser user = userAuth.getCurrentUser();
                                Intent intent = new Intent(getApplicationContext(),Add_event.class);
                                startActivity(intent);
                            } else {
                                try{
                                    throw task.getException();
                                } catch(FirebaseAuthInvalidUserException e) {
                                    userPasswd.setError("Invalid Email");
                                    userPasswd.requestFocus();
                                } catch(FirebaseAuthInvalidCredentialsException e) {
                                    userMail.setError(getString(R.string.error_invalid_email));
                                    userMail.requestFocus();
                                } catch(FirebaseAuthWeakPasswordException e) {
                                    userMail.setError("Wrong Password");
                                    userMail.requestFocus();
                                } catch(Exception e) {
                                    Log.e("gg", e.getMessage());
                                }
                            }
                            Toast.makeText(log_in.this, "creating account failed !", Toast.LENGTH_LONG).show();
                            Log.e("prrrrrr ma mchaaatech", "mamchaaaatech");
                        }

                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please check your internet Connection", Toast.LENGTH_LONG).show();
        }
    }





}
