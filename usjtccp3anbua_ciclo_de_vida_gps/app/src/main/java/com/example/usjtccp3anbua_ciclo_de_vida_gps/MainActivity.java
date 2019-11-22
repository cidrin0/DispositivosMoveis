package com.example.usjtccp3anbua_ciclo_de_vida_gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;

    private double latitude, longitude;

    private static final int REQUEST_PERMISSION_CODE_GPS = 1001;

    private ListCircle<Location> locations = new ListCircle<>(50);

    private RecyclerView weatherRecyclerView;
    private WeatherAdapter adapter;
    private List<Weather> previsoes;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        weatherRecyclerView =
                findViewById(R.id.weatherRecyclerView);
        previsoes = new ArrayList<>();
        adapter = new WeatherAdapter(previsoes, this);
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        weatherRecyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        Location locationMock = new Location("provider");
        locationMock.setLatitude(-23);
        locationMock.setLongitude(-46);
        locations.add(locationMock);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                addLocation(location);

                latitude = location.getLatitude();
                longitude = location.getLongitude();
                obtemPrevisoes(latitude, longitude);
            }

            private void addLocation(Location location) {
                System.out.println("passei e adicionei uma localização");
                locations.add(location);
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
                    1000,
                    0,
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
                            2,
                            1,
                            locationListener
                    );
                }
            } else {
                Toast.makeText(this, getString(R.string.no_gps_no_app), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void obtemPrevisoes(double latitude, double longitude) {
        String url = getString(
                R.string.web_service_url,
                latitude, longitude,
                getString(R.string.api_key)
        );
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                (response) -> {
                    previsoes.clear();
                    try {
                        JSONArray list = response.getJSONArray("list");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject day = list.getJSONObject(i);
                            JSONObject main = day.getJSONObject("main");
                            JSONObject weather = day.getJSONArray("weather").getJSONObject(0);
                            previsoes.add(new Weather(day.getLong("dt"), main.getDouble("temp_min"),
                                    main.getDouble("temp_max"), main.getDouble("humidity"),
                                    weather.getString("description"), weather.getString("icon")));
                        }
                        adapter.notifyDataSetChanged();
                        dismissKeyboard(weatherRecyclerView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                (error) -> {
                    Toast.makeText(
                            MainActivity.this,

                            getString(R.string.connect_error) + ": " + error.getLocalizedMessage(),

                            Toast.LENGTH_SHORT
                    ).show();
                }
        );
        requestQueue.add(req);
    }

    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}