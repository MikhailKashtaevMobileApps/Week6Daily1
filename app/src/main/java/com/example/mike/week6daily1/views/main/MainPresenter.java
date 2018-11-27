package com.example.mike.week6daily1.views.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.mike.week6daily1.google_apis.GooglePlaces;
import com.example.mike.week6daily1.google_apis.Place;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class MainPresenter implements MainContract.Presenter, LocationListener {

    MainContract.View view;
    LatLng currentLocation;
    public static final String TAG = "__TAG__";
    private String types;

    public MainPresenter() {
        currentLocation = new LatLng(-34, 151);
    }

    @Override
    public void onAttach(MainContract.View view) {
        this.view = view;

    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void goPlaces(String types) {
        this.types = types;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (((Context) this.view).checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) this.view, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                onGoPlaces();
            }
            return;
        }
        onGoPlaces();
    }

    @Override
    public void onPermissionsGranted() {
        onGoPlaces();
    }

    @SuppressLint("MissingPermission")
    private void onGoPlaces() {
        FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient((Activity) this.view);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                GooglePlaces.getPlacesAround("" + location.getLatitude() + "," + location.getLongitude(),
                        "food"
                        ,
                        new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                                try {
                                    String resp = response.body().string();
                                    JSONArray jsonArray = new JSONObject(resp).getJSONArray("results");

                                    ArrayList<Place> places = new ArrayList<>();

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        LinkedList<String> types = new LinkedList<>();
                                        for (int j = 0; j < jsonArray.getJSONObject(i).getJSONArray("types").length(); j++) {
                                            types.add( jsonArray.getJSONObject(i).getJSONArray("types").getString(j) );
                                        }

                                        places.add( new Place(
                                                new LatLng(
                                                        jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                                                        jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng")
                                                ),
                                                jsonArray.getJSONObject(i).getString("name"),
                                                types.toString(),
                                                jsonArray.getJSONObject(i).getString("icon")
                                                )
                                        );
                                    }
                                    view.onRetrievePlaces( places );
                                } catch (IOException | JSONException e) {
                                    Log.d(TAG, "onResponse: Error="+e.getMessage());
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.d(TAG, "onFailure: "+t.getMessage());
                            }
                        });
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
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
}
