package com.applefish.ibuffeh;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
     //component
    private Spinner spinner;
    private Button button;
    //get JSON Object
    private String jsonResult;
    private static final String TAG_RESULTS = "Data";
    private static JSONArray jArray = null;
    //Map
    private GoogleMap mMap;

    //Url to get cities name from server
    private static final String url = "http://5.189.171.31:2323/api/Common/ListCities?languageCode=ar";
    private ArrayAdapter<String> All_cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        spinner = (Spinner) findViewById(R.id.citiesSpinner);
        button = (Button) findViewById(R.id.btn1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), MainActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

                startActivity(intent);

                overridePendingTransition(R.anim.right_in, R.anim.left_out);

          }
        });

        getJSON(url, true);
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

        getCurrent(mMap);
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }
    private void getJSON(String url, boolean check) {


        class GetJSON extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(MapsActivity.this);

            @Override
            protected void onPreExecute() {
                this.dialog.setMessage("Loading Cities.....");
                this.dialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                String result = "";
                BufferedReader bufferedReader = null;
                try {
                    String UTF8 = "utf8";
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    result = sb.toString().trim();
                    return result;

                } catch (Exception e) {
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                jsonResult = result;

                try {
                    if (jsonResult != null) {
                        JSONObject jsonObj = new JSONObject(jsonResult);
                        jArray = jsonObj.getJSONArray(TAG_RESULTS);

                        String[] cities = new String[jArray.length()];
                         //get city name from JSONArray
                        for (int i = 0; i < jArray.length(); i++) {
                            String city = jArray.get(i).toString();

                            cities[i] = city.toString();
                        }
                        Log.i("", cities[0]);

                        All_cities = new ArrayAdapter<String>(getBaseContext(), android.R.layout.preference_category, cities);
                        All_cities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(All_cities);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (dialog.isShowing())
                    dialog.dismiss();

            }

            @Override
            protected void onCancelled() {
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        }
        GetJSON gj = new GetJSON();
        if (check)
            gj.execute(url);
        else {
            if (!gj.isCancelled())
                gj.cancel(true);
        }

    }

    private void getCurrent(final GoogleMap mMap) {

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (!service.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);

        }

        LocationListener listener = new LocationListener() {

            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderEnabled(String arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderDisabled(String arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLocationChanged(Location location) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("It's Me!"));
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                mMap.animateCamera(zoom);

            }
        };
        //check Permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        service.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        service.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 0, listener );

    }
}
