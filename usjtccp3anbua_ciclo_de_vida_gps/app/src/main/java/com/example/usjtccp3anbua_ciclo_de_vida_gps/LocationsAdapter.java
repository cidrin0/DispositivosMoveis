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
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            LocationViewHolder locationViewHolder = new LocationViewHolder(
                    convertView.findViewById(R.id.lccationIconImageView),
                    convertView.findViewById(R.id.latitudeFilaTextView),
                    convertView.findViewById(R.id.longitudeTextView));
            convertView.setTag(locationViewHolder);
        }
        LocationViewHolder locationViewHolder = (LocationViewHolder) convertView.getTag();

        locationViewHolder.locationImageView.setImageResource(R.drawable.ic_location_on_black_24dp);
        locationViewHolder.latitudeTextView.setText(String.valueOf(location.getLatitude()));
        locationViewHolder.longitudeTextView.setText(String.valueOf(location.getLongitude()));

        return convertView;
    }

    private class LocationViewHolder {
        public ImageView locationImageView;
        public TextView longitudeTextView;
        public TextView latitudeTextView;

        public LocationViewHolder(ImageView locationImageView, TextView longitudeTextView, TextView latitudeTextView) {
            this.locationImageView = locationImageView;
            this.longitudeTextView = longitudeTextView;
            this.latitudeTextView = latitudeTextView;
        }
    }
}
