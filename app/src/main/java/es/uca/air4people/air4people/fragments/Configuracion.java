package es.uca.air4people.air4people.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.ArrayList;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.ContaminacionHelper;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.recicler.ReciclerSuscripciones.Suscripcion;

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