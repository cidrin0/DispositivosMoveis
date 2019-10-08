package com.example.usjtccp3anbua_ciclo_de_vida_gps;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LocationsAdapter extends ArrayAdapter<Location> {

    public LocationsAdapter(Context context, List<Location> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Location location = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent,false);
        }
        ImageView filaIconImageView = convertView.findViewById(R.id.lccationIconImageView);
        TextView latitudeTextView = convertView.findViewById(R.id.latitudeFilaTextView);
        TextView longitudeTextView = convertView.findViewById(R.id.longitudeTextView);

        filaIconImageView.setImageResource(R.drawable.ic_location_on_black_24dp);
        latitudeTextView.setText(String.valueOf(location.getLatitude()));
        longitudeTextView.setText(String.valueOf(location.getLongitude()));

        return convertView;
    }
}
