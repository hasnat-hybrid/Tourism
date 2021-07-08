package org.hasnat.tourism;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class profile extends AppCompatActivity {

    //Attributes to retrieve
    TextView mFullName,mEmail,mBookings,mPhone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //assigning values
        mFullName=findViewById(R.id.pName);
        mEmail=findViewById(R.id.pEmail);
        mPhone=findViewById(R.id.pPhone);
        mBookings=findViewById(R.id.pBooking);

        //getting firebase Instances to
        //1. authorize 2. store
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //getting user-id
        userID=fAuth.getCurrentUser().getUid();

        //for user details
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    mPhone.setText(documentSnapshot.getString("phone"));
                    mFullName.setText(documentSnapshot.getString("fName"));
                    mEmail.setText(documentSnapshot.getString("email"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        //for booking-check
        DocumentReference documentReference2 = fStore.collection("bookings").document(userID);
        documentReference2.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    mBookings.setText(documentSnapshot.getString("bPlace"));
                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
    }
}
