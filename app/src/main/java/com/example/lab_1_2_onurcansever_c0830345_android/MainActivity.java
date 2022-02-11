package com.example.lab_1_2_onurcansever_c0830345_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;

import com.example.lab_1_2_onurcansever_c0830345_android.databinding.ActivityMainBinding;
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

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mapView;
    private ActivityMainBinding binding;

    public static final int REQUEST_CODE = 1;
    public static final int UPDATE_INTERVAL = 5000;
    public static final int FASTEST_INTERVAL = 3000;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private double chosenLongitude = 0;
    private double chosenLatitude = 0;

    private Marker marker, customMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        binding.showProductsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductActivity.class);

            intent.putExtra("savedLongitude", chosenLongitude);
            intent.putExtra("savedLatitude", chosenLatitude);

            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapView = googleMap;
        mapView.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (!isGrantedLocationPermission()) {
            requestLocationPermission();
        } else {
            startUpdatingLocation();
        }

        mapView.setOnMapLongClickListener(latLng -> {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title("Product Location");

            customMarker = mapView.addMarker(options);

        });

    }

    private void startUpdatingLocation() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                //mMap.clear();
                Location location = locationResult.getLastLocation();
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                chosenLongitude = userLocation.longitude;
                chosenLatitude = userLocation.latitude;

                marker = mapView.addMarker(new MarkerOptions().position(userLocation).title("Product location"));
                mapView.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15.0f));

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    private boolean isGrantedLocationPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (REQUEST_CODE == requestCode) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setMessage("The permission is mandatory to be granted.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                            }
                        }).create().show();
            } else {
                startUpdatingLocation();
            }
        }
    }
}