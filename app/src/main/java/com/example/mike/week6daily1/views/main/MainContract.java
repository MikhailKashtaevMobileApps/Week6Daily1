package com.example.mike.week6daily1.views.main;

import com.example.mike.week6daily1.base.BasePresenter;
import com.example.mike.week6daily1.base.BaseView;
import com.example.mike.week6daily1.google_apis.Place;

import java.util.List;

public interface MainContract {

    interface View extends BaseView{
        void onRetrievePlaces(List<Place> places);
    }

    interface Presenter extends BasePresenter{
        void onAttach( View view );
        void onDetach();
        void goPlaces( String types );
        void onPermissionsGranted();
    }

}
