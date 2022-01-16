package com.example.tracker;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.tracker.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    FusedLocationProviderClient fusedLocationClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;
    Marker loc;
    LatLng prev = null;
    LatLng cur = null;
    double dist;
    TextView txt;
    Chronometer timer;
    PolylineOptions polylineOpts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txt = findViewById(R.id.textView);
        timer = findViewById(R.id.chronometer);

        startLocationUpdates();

    }

    public void stop(View view) {
        long elapsedMillis = SystemClock.elapsedRealtime() - timer.getBase();
        timer.stop();
        int timemins = Math.round(elapsedMillis / 60000);
        Intent i = new Intent(this, SummaryActivity.class);
        i.putExtra("Timer", timemins);
        i.putExtra("Dist", dist);
        startActivity(i);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        return;
    }

    private float distance(LatLng pre, LatLng curr) {
        double phi2 = curr.latitude , phi1 = pre.latitude ;
        float d[] = new float[1];
        android.location.Location.distanceBetween(phi1, pre.longitude, phi2, cur.longitude, d);
        return d[0];
    }

    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Location permission revoked, please reinstate the permission to continue",Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();

        polylineOpts = new PolylineOptions();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                double lat = 0, lng = 0, len = locationResult.getLocations().size();
                for (Location location : locationResult.getLocations()) {
                    lat += location.getLatitude();
                    lng += location.getLongitude();
                }
                lat /= len;
                lng /= len;
                prev = cur;
                cur = new LatLng(lat, lng);
                if (prev == null) {
                    dist = 0;
                } else {
                    dist += distance(cur, prev);
                }
                float display_dist = 0;
                if (dist > 1000) {
                    display_dist = (float)(Math.round(dist / 10)) / 100;
                    txt.setText(String.valueOf(display_dist) + "km");
                } else {
                    display_dist = (int)(dist);
                    txt.setText(String.valueOf(display_dist) + "m");
                }
                if (loc != null) {
                    loc.remove();
                }
                loc = mMap.addMarker(new MarkerOptions().position(cur).title("CurrentLocation"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(cur));
                polylineOpts.add(cur);
                mMap.addPolyline(polylineOpts);
            }
        };
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(30000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());


    }
}