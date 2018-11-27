package com.example.mike.week6daily1.google_apis;

import android.content.res.Resources;

import com.example.mike.week6daily1.R;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class GooglePlaces {
    public static final String KEY = "AIzaSyDps6VHJVNUn-RXm9Dk1rPvUJUnkfHp278";
    public static final String initialURL = "location={location}&types={types}&key={key}";

    public static void getPlacesAround(String location, String types, Callback<ResponseBody> callback ){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/")
                .build();

        retrofit.create(GooglePlacesService.class).getPlaces(location, types, KEY, "500" ).enqueue(callback);
    }

    interface GooglePlacesService{

        @GET("json")
        Call<ResponseBody> getPlaces(@Query("location") String location, @Query("types") String types, @Query("key") String key, @Query("radius") String radius );
    }

}
