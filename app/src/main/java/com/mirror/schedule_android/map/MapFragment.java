package com.mirror.schedule_android.map;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mirror.schedule_android.R;
import com.mirror.schedule_android.schedule.ScheduleFragment;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback{

    View v;

    private MapView mapView;
    private static NaverMap mNaverMap;
    private Marker marker = new Marker();

    private TextView location, rest, schedule, title;

    private Spinner spinner;
    ArrayList<String> titles = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.mapfragment, container, false);
        location = (TextView) v.findViewById(R.id.location);
        rest = (TextView) v.findViewById(R.id.rest);
        schedule = (TextView) v.findViewById(R.id.schedule);
        title = (TextView) v.findViewById(R.id.title);

        mapView = (MapView) v.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        for(int i = 0; i < ScheduleFragment.mySubjects.size(); i++) {
            titles.add(ScheduleFragment.mySubjects.get(i).getTitle());
        }
        spinner = (Spinner) v.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item, titles
        );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                title.setText(ScheduleFragment.mySubjects.get(i).getTitle() + " - " + ScheduleFragment.mySubjects.get(i).getProfessor());
                rest.setText(ScheduleFragment.mySubjects.get(i).getRest());
                location.setText(ScheduleFragment.mySubjects.get(i).getLocation());
                schedule.setText(ScheduleFragment.mySubjects.get(i).getSchedules());
                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_baseline_location_on_24));
                marker.setPosition(new LatLng(Double.parseDouble(ScheduleFragment.mySubjects.get(i).getLatitude()),
                        Double.parseDouble(ScheduleFragment.mySubjects.get(i).getLongitude())));
                marker.setMap(mNaverMap);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return v;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap)
    {
        //example location

        marker.setIconPerspectiveEnabled(true);
        marker.setIcon(OverlayImage.fromResource(R.drawable.ic_baseline_location_on_24));
//        marker.setPosition(new LatLng(
//                Double.parseDouble(ScheduleFragment.mySubjects.get(0).getLatitude()),
//                Double.parseDouble(ScheduleFragment.mySubjects.get(0).getLongitude())));
        //35.845996
        //127.1344457
        marker.setPosition(new LatLng(
                Double.parseDouble("35.845996"),
                Double.parseDouble("127.1344457")));
        marker.setMap(naverMap);
        mNaverMap = naverMap;

        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(35.845996,
                        127.1344457),
                15,
                5,
                0
        );
        mNaverMap.setCameraPosition(cameraPosition);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}