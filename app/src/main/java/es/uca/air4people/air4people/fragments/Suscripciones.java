package es.uca.air4people.air4people.fragments;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.uca.air4people.air4people.ComprobarContaminacion;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.ReciclerEstaciones.AdaptadorEstacionesMediciones;
import es.uca.air4people.air4people.ReciclerSuscripciones.AdaptadorSuscripciones;
import es.uca.air4people.air4people.Servicio.EstacionService;
import es.uca.air4people.air4people.Servicio.Medicion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Suscripciones extends Fragment {

    public Suscripciones() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.content_suscripciones, container, false);

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://airservices.uca.es/Air4People/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        EstacionService estacionService = retrofit.create(EstacionService.class);
        Call<List<String>> call = estacionService.getContaminantes();

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                final AdaptadorSuscripciones adaptador = new AdaptadorSuscripciones((ArrayList<String>) response.body());
                RecyclerView rec=view.findViewById(R.id.recCon);
                rec.setAdapter(adaptador);
                rec.setLayoutManager(
                        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
                rec.addItemDecoration(
                        new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                rec.setItemAnimator(new DefaultItemAnimator());

            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        return view;
    }
}