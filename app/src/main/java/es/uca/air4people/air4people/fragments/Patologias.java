package es.uca.air4people.air4people.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.ReciclerPatologias.AdaptadorPatologias;
import es.uca.air4people.air4people.ReciclerSuscripciones.AdaptadorSuscripciones;
import es.uca.air4people.air4people.Servicio.EstacionService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Patologias extends Fragment {

    public Patologias() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.content_suscripciones, container, false);

        final AdaptadorPatologias adaptador = new AdaptadorPatologias(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.patologias))));
        RecyclerView rec=view.findViewById(R.id.recCon);
        rec.setAdapter(adaptador);
        rec.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        rec.addItemDecoration(
                new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        rec.setItemAnimator(new DefaultItemAnimator());
        return view;
    }
}