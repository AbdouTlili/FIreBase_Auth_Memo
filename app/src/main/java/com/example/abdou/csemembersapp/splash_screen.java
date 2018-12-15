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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class splash_screen extends AppCompatActivity {


    private FirebaseAuth userAuth;
    Button logInButt ;
    EditText userMail;
    EditText userPasswd ;
    public boolean logedIn=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

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
                    addUser(isConnected);
                }
            });

        }
        catch (Exception e){Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();}




    }



void addUser(boolean isConnected){
    if (isConnected) {
        String mail = userMail.getText().toString();
        String password = userPasswd.getText().toString();
        userAuth = FirebaseAuth.getInstance();
        userAuth.createUserWithEmailAndPassword(mail, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("prrrrrrrrrr mchaaat", "mchaaaaaaaaaaaaaaaat");
                            Toast.makeText(splash_screen.this, "creating account done !", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(),Add_event.class);
                            startActivity(intent);

                        } else {
                                try{
                                    throw task.getException();
                                } catch(FirebaseAuthWeakPasswordException e) {
                                    userPasswd.setError(getString(R.string.error_weak_password));
                                    userPasswd.requestFocus();
                                } catch(FirebaseAuthInvalidCredentialsException e) {
                                    userMail.setError(getString(R.string.error_invalid_email));
                                    userMail.requestFocus();
                                } catch(FirebaseAuthUserCollisionException e) {
                                    userMail.setError(getString(R.string.error_user_exists));
                                    userMail.requestFocus();
                                } catch(Exception e) {
                                    Log.e("gg", e.getMessage());
                                }
                            }
                            Toast.makeText(splash_screen.this, "creating account failed !", Toast.LENGTH_LONG).show();
                            Log.e("prrrrrr ma mchaaatech", "mamchaaaatech");
                        }

                });
    } else {
        Toast.makeText(getApplicationContext(), "Please check your internet Connection", Toast.LENGTH_LONG).show();
    }
}




}
