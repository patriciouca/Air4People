package es.uca.air4people.air4people.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

public class Mapa extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private FragmentManager general;
    private View vgeneral;
    MapView mMapView;
    private GoogleMap mMap;

    public Mapa() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_mapa, container, false);
        general=getActivity().getSupportFragmentManager();
        vgeneral=view;
        mMapView= view.findViewById(R.id.mapView2);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://airservices.uca.es/Air4People/").
                addConverterFactory(GsonConverterFactory.create())
                .build();

        EstacionService estacionService = retrofit.create(EstacionService.class);
        Call<List<Estacion>> call = estacionService.getMapa();

        call.enqueue(new Callback<List<Estacion>>() {
            @Override
            public void onResponse(Call<List<Estacion>> call, Response<List<Estacion>> response) {
                CameraUpdate center=null;
                for (Estacion a:response.body()){
                    LatLng punto = new LatLng(a.getLongitude(), a.getLatitude());
                    MarkerOptions marcador=new MarkerOptions();
                    marcador.position(punto);
                    marcador.title(a.getMote_name());
                    marcador.snippet("Marker Description");
                    mMap.addMarker(marcador);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(punto));
                    center=CameraUpdateFactory.newLatLng(punto);

                }
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(6);
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);
            }
            @Override
            public void onFailure(Call<List<Estacion>> call, Throwable t) {
            }
        });

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        general.beginTransaction().add(R.id.detalle, new MapaDetalle()).commit();

        ConstraintLayout constraint = vgeneral.findViewById(R.id.reglasprincipal);
        ConstraintSet set=new ConstraintSet();
        set.clone(constraint);

        set.applyTo(constraint);
        return false;
    }
}