package com.example.usjtccp3anbua_ciclo_de_vida_gps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;

    private double latitude, longitude;

    private static final int REQUEST_PERMISSION_CODE_GPS = 1001;

    private TextView locationTextView;
    private EditText searchText;

    private ArrayList<Location> locations = new ArrayList<>(50);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationTextView = findViewById(R.id.locationTextView);
        searchText = findViewById(R.id.searchText);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("This is your locations ");
                locations.forEach(System.out::println);

                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse(String.format("geo:%f,%f?q=%s", latitude, longitude, searchText.getText().toString()));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                addLocation(location);

                latitude = location.getLatitude();
                longitude = location.getLongitude();
                String s = String.format(
                        Locale.getDefault(),
                        "Lat: %f, Long: %f",
                        latitude, longitude
                );
                locationTextView.setText(s);

            }

            private void addLocation(Location location) {
                locations.set(locations.size() % 50,  location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000 * 60 * 2,
                    200,
                    locationListener
            );
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION_CODE_GPS
            );
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE_GPS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            2000,
                            10,
                            locationListener
                    );
                }

            } else {
                Toast.makeText(this, getString(R.string.no_gps_no_app), Toast.LENGTH_SHORT).show();
            }
        }
    }
}