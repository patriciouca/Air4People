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

import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.recicler.ReciclerPatologias.AdaptadorPatologias;

public class Patologias extends Fragment {

    public Patologias() {
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