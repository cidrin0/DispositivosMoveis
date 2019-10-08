package com.example.usjtccp3anbua_ciclo_de_vida_gps;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

public class ListLocationActivity extends AppCompatActivity {

    private ListView locationsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_location);
        locationsListView = findViewById(R.id.locationsListView);

        Intent intentOrigem = getIntent();
        List<Location> locations = (List<Location>) intentOrigem.getSerializableExtra("locations");

        LocationsAdapter adapter = new LocationsAdapter(this, locations);
        locationsListView.setAdapter(adapter);
        locationsListView.setOnItemClickListener((adapterView, view, position, id) -> {
            Location location = locations.get(position);
            Uri uri = Uri.parse(String.format("geo:%f,%f", location.getLatitude(), location.getLongitude()));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
        });
    }
}

