package es.uca.air4people.air4people;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;


import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.Servicio.Estacion;
import es.uca.air4people.air4people.Servicio.EstacionService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Mapa extends Fragment implements OnMapReadyCallback {

    MapView mMapView;
    private GoogleMap mMap;

    public Mapa() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_mapa, container, false);
        mMapView=(MapView)view.findViewById(R.id.mapView2);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                //mMap.setMyLocationEnabled(true);
                //mMap.setOnMyLocationButtonClickListener(this);
                //mMap.setOnMyLocationClickListener(this);

                // For dropping a marker at a point on the Map
                LatLng espana = new LatLng(40.416775, -3.703790);

                /*
                call.enqueue(new Callback<List<Estacion>>() {
                    @Override
                    public void onResponse(Call<List<Estacion>> call, Response<List<Estacion>> response) {
                        for (Estacion a:response.body()){
                            //LatLng nuevo = new LatLng(a.getLatitude(), a.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(new LatLng(a.getLatitude(), a.getLongitude())).title(a.getMote_name()).snippet("Marker Description"));
                        }

                        Log.d("Raro","Terminado");
                    }
                    @Override
                    public void onFailure(Call<List<Estacion>> call, Throwable t) {
                    }
                });

                Retrofit retrofit = new Retrofit.Builder().
                        baseUrl("http://airservices.uca.es/Air4People/").
                        addConverterFactory(GsonConverterFactory.create())
                        .build();

                EstacionService estacionService = retrofit.create(EstacionService.class);
                Call<List<Estacion>> call = estacionService.getMapa();

                Response< List<Estacion> > response=null;

                try{

                    Log.d("Raro",String.valueOf(call.request()));
                    response=call.execute();

                }catch(Exception e){

                    Log.d("Raro",String.valueOf(response.code()));
                }
                */
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(espana).zoom(8).build();
                //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }


}