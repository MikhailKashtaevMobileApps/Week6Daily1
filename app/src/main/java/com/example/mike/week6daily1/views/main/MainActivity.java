package com.example.mike.week6daily1.views.main;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.mike.week6daily1.R;
import com.example.mike.week6daily1.google_apis.Place;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements MainContract.View {

    public static final String TAG = "__TAG__";
    private MainPresenter presenter;
    private PlacesAdapter adapter;
    private RecyclerView rvPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter();
        presenter.onAttach(this);
        presenter.goPlaces("");

        rvPlaces = findViewById( R.id.rvPlaces );
        adapter = new PlacesAdapter(new ArrayList<Place>());
        rvPlaces.setLayoutManager(new LinearLayoutManager(this));
        rvPlaces.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ( requestCode == 1 ){
            if ( grantResults[0] == PackageManager.PERMISSION_GRANTED){
                presenter.onPermissionsGranted();
            }
        }
    }

    @Override
    public void onRetrievePlaces(List<Place> places) {
        Log.d(TAG, "onRetrievePlaces: "+places.toString());
        adapter.places = places;
        adapter.notifyDataSetChanged();
    }
}
