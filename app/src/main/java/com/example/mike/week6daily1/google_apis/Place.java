package com.example.mike.week6daily1.google_apis;

import com.google.android.gms.maps.model.LatLng;

public class Place {

    LatLng location;
    String name;
    String types;
    String icon;

    public Place(LatLng location, String name, String types, String icon) {
        this.location = location;
        this.name = name;
        this.types = types;
        this.icon = icon;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
