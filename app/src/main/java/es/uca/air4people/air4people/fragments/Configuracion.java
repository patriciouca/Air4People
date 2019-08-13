package es.uca.air4people.air4people.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Arrays;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.ContaminacionHelper;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.ReciclerSuscripciones.Suscripcion;
import es.uca.air4people.air4people.memoria.MemoriaAplicacion;

public class Configuracion extends Fragment {

    public Configuracion() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.content_configuraciones, container, false);
        final AndroidBaseDatos bd=new AndroidBaseDatos(getContext());

        Switch interruptor=view.findViewById(R.id.activoPatologias);

        interruptor.setChecked(bd.isMarcado());


        ArrayList<Suscripcion> suscripcions=bd.getSuscripciones();
        for (int i=0;i<suscripcions.size();i++)
        {
            Log.d("SUSCRIBIR ",suscripcions.get(i).nombre);
        }

        interruptor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bd.marcar(isChecked);
                if(isChecked)
                    ContaminacionHelper.inferir(getContext());
            }
        });
        return view;
    }
}