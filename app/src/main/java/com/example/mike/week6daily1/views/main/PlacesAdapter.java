package com.example.mike.week6daily1.views.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mike.week6daily1.R;
import com.example.mike.week6daily1.google_apis.Place;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {
    List<Place> places;

    public PlacesAdapter(List<Place> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.places_adapter_item, viewGroup, false );
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Place p = places.get(i);

        viewHolder.placeName.setText( p.getName() );
        viewHolder.placeType.setText( p.getTypes() );
        viewHolder.placeLocation.setText( p.getLocation().toString() );

    }

    @Override
    public int getItemCount() {
        return this.places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView placeName;
        TextView placeType;
        TextView placeLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById( R.id.placeName );
            placeType = itemView.findViewById( R.id.placeTypes );
            placeLocation = itemView.findViewById( R.id.placeLocation );
        }
    }

}
