package org.hasnat.tourism;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    //local attributes
    EditText mEmail,mPassword;
    Button mLoginBtn;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning XML-Values to attributes
        mEmail = findViewById(R.id.email0);
        mPassword = findViewById(R.id.password0);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.button0);



        //to-chcek current user
        //if yes then re-direct to home1
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),home1.class));
            Toast.makeText(MainActivity.this, "You Already Logged-In.", Toast.LENGTH_SHORT).show();
            finish();
        }

        //onclick at login-button
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            //checking the data validation
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }


                // authenticate the user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),home1.class));
                        }else {
                            Toast.makeText(MainActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }


    //new-user to register
    public void Register(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }


    //--
    // -----------Menu-Functions Ends here
}
