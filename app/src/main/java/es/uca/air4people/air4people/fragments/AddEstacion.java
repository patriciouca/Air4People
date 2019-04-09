package es.uca.air4people.air4people.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.ReciclerEstaciones.AdaptadorEstacion;
import es.uca.air4people.air4people.ReciclerEstaciones.EstacionLista;
import es.uca.air4people.air4people.ReciclerMedicionFecha.AdaptadorMedicionesFecha;
import es.uca.air4people.air4people.Servicio.Estacion;
import es.uca.air4people.air4people.Servicio.EstacionService;
import es.uca.air4people.air4people.Servicio.Medicion;
import es.uca.air4people.air4people.Servicio.Mediciones;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddEstacion extends Fragment {

    public AddEstacion() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View vista=inflater.inflate(R.layout.content_addestacion, container, false);
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://airservices.uca.es/Air4People/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        final RecyclerView rec=vista.findViewById(R.id.addestacion);
        EstacionService estacionService = retrofit.create(EstacionService.class);
        Call<List<String>> call = estacionService.getEstaciones();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, final Response<List<String>> response) {

                rec.setHasFixedSize(true);
                Collections.sort(response.body());

                AdaptadorEstacion adaptador=new AdaptadorEstacion(new ArrayList<String>(response.body()));
                rec.setAdapter(adaptador);

                rec.setLayoutManager(
                        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));


                rec.setItemAnimator(new DefaultItemAnimator());

                rec.addItemDecoration(
                        new DividerItemDecoration(vista.getContext(), DividerItemDecoration.VERTICAL));


                rec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
            }
        });
        return vista;
    }
}