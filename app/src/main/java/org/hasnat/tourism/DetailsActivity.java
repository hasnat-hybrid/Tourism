package org.hasnat.tourism;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID,mPlace;
    public static final String TAG = "TAG";
    Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //booking button
        bookButton= findViewById(R.id.bookButton);
        //getting firebase Instances to
        //1. authorize 2. store
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //getting user-id
        userID=fAuth.getCurrentUser().getUid();


        //object-spinner
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
        //for-spinner
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(DetailsActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.places));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        //After clicking button

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //object-spinner
                Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
                mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        String selectedItem = parent.getItemAtPosition(position).toString();
                        sendPlace(selectedItem);
                    } // to close the onItemSelected
                    public void onNothingSelected(AdapterView<?> parent)
                    {
                        Toast.makeText(DetailsActivity.this, "You Selected Nothing.", Toast.LENGTH_SHORT).show();
                        goToHome();
                    }
                });

            }
        });




    }

    public void goToHome() {
        Intent intent = new Intent(this, home1.class);
        startActivity(intent);
    }



    public void sendPlace(String s){
        DocumentReference documentReference = fStore.collection("bookings").document(userID);
        Map<String,Object> user = new HashMap<>();
        user.put("bPlace",s);

        //For IDE-log reference
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: done "+ userID);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }
        });

        //message
        Toast.makeText(DetailsActivity.this, "You Booked "+s+" Tour Successfully.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),home1.class));
    }
}
