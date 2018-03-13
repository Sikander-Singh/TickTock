package com.example.sikandermangat.ticktock.Helpers;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sikander Mangat on 2018-03-11.
 */

public class LocationService extends FragmentActivity implements LocationListener {

    private LocationManager locationManager;
    private String locationName;
    private final static int REQUEST_LOCATION = 1;
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    private void alertNoGps(Context context) {


        final AlertDialog.Builder builder=new AlertDialog.Builder(context);

        builder.setMessage("Please Turn on your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        final AlertDialog alert=builder.create();

        alert.show();

    }

    public String location(Context context,FragmentActivity activity) throws IOException {

        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return locationName;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, (LocationListener)this);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            alertNoGps(context);
        }
        else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            locationName=Userlocation(context,activity);

        }

     return locationName;
    }
    private String Userlocation(Context context,FragmentActivity activity) throws IOException {


        String locName="";

        if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){


            ActivityCompat.requestPermissions(activity,new  String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);

        }
        else{


            Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(location!=null){

                double lat=location.getLatitude();
                double lon=location.getLongitude();
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(activity, Locale.getDefault());
                addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                locName=city;

            }

        }

        return locName;
    }



}
