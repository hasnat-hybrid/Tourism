package org.hasnat.tourism;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.hasnat.tourism.Adapter.RecentsAdapter;
import org.hasnat.tourism.Adapter.TopPlacesAdapter;
import org.hasnat.tourism.model.RecentsData;
import org.hasnat.tourism.model.TopPlacesData;

import java.util.ArrayList;
import java.util.List;


public class home1 extends AppCompatActivity {

    //recycler-view Vars
    RecyclerView recentRecycler, topPlacesRecycler;
    RecentsAdapter recentsAdapter;
    TopPlacesAdapter topPlacesAdapter;
    private ImageView profile0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        //profile-image object
         profile0=(ImageView) findViewById(R.id.imageView);
        //making it a menu
        profile0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(home1.this, profile0);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();

                        if (id == R.id.profile) {
                            startActivity(new Intent(getApplicationContext(), profile.class));
                            return true;
                        }

                        if (id == R.id.logout) {
                            logout();
                            return true;
                        }
                        else
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

        //updating recycler-view1 data
        List<RecentsData> recentsDataList = new ArrayList<>();
        recentsDataList.add(new RecentsData( "Attabad Lake" ,"Pakistan" ,"From Rs.200", R.drawable.att2));
        recentsDataList.add(new RecentsData( "Swat Valley" ,"Pakistan" ,"From Rs.300", R.drawable.swatv));
        recentsDataList.add(new RecentsData( "Yarkhun Valley" ,"Pakistan" ,"From Rs.200", R.drawable.yarv));
        recentsDataList.add(new RecentsData( "Passu Cones" ,"Pakistan" ,"From Rs.300", R.drawable.passu));
        recentsDataList.add(new RecentsData( "Hunza Valley" ,"Pakistan" ,"From Rs.200", R.drawable.attlake));
        recentsDataList.add(new RecentsData( "Boyun Village" ,"Pakistan" ,"From Rs.300", R.drawable.bvillage));

        //calling the function
        setRecentRecycler(recentsDataList);

        //updating recycler-view2 data
        List<TopPlacesData> topPlacesDataList = new ArrayList<>();
        topPlacesDataList.add(new TopPlacesData("Boyun Village","Pakistan","Rs.200 - Rs.500",R.drawable.bvillage));
        topPlacesDataList.add(new TopPlacesData("Hunza Valley","Pakistan","Rs.200 - Rs.500",R.drawable.attlake));
        topPlacesDataList.add(new TopPlacesData("Passu Cones","Pakistan","Rs.200 - Rs.500",R.drawable.passu));
        topPlacesDataList.add(new TopPlacesData("Yarkhun Valley","Pakistan","Rs.200 - Rs.500",R.drawable.yarv));
        topPlacesDataList.add(new TopPlacesData("Swat Valley","Pakistan","Rs.200 - Rs.500",R.drawable.swatv));

        setTopPlacesRecycler(topPlacesDataList);

    }

    //For the recent-recycler-view
    //In the Home-layout
    private void setRecentRecycler(List<RecentsData> recentsDataList){
        recentRecycler = findViewById(R.id.recent_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this , RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        recentsAdapter = new RecentsAdapter(this, recentsDataList);
        recentRecycler.setAdapter(recentsAdapter);
    }

    // to update 2nd recycler
    private  void setTopPlacesRecycler(List<TopPlacesData> topPlacesDataList){

        topPlacesRecycler = findViewById(R.id.top_places_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        topPlacesRecycler.setLayoutManager(layoutManager);
        topPlacesAdapter = new TopPlacesAdapter(this, topPlacesDataList);
        topPlacesRecycler.setAdapter(topPlacesAdapter);

    }

    //logout function
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        Toast.makeText(home1.this, "Successfully Logged out.", Toast.LENGTH_SHORT).show();
        finish();
    }

}
