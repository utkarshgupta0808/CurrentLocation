package com.example.currentlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Geocoder geocoder;
    List<Address> addresses;
    FusedLocationProviderClient fusedLocationProviderClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=  (TextView) findViewById(R.id.textView);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location=task.getResult();
                if(location!=null){
                    try {
                        geocoder= new Geocoder(MainActivity.this,Locale.getDefault());
                        addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        String address= addresses.get(0).getAddressLine(0);
                        String city= addresses.get(0).getLocality();
                        String state= addresses.get(0).getAdminArea();
                        String  country= addresses.get(0).getCountryName();
                        String  postalcode= addresses.get(0).getPostalCode();

                        String fulladdress= address+", "+city+", "+state+", "+country+", "+postalcode;
                        textView.setText(fulladdress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });





    }
}
