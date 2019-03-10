package es.uca.air4people.air4people.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import es.uca.air4people.air4people.EstacionesActivity;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.lista.AdapterEstacion;
import es.uca.air4people.air4people.lista.EstacionLista;

public class ListaMisEstaciones extends Fragment {

    public ListaMisEstaciones() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_listaestaciones, container, false);

        final ArrayList<EstacionLista> estaciones = new ArrayList<EstacionLista>();
        estaciones.add(new EstacionLista("Hola",25));
        estaciones.add(new EstacionLista("Adios",45));
        estaciones.add(new EstacionLista("Remedio",35));


        ListView lv = (ListView) view.findViewById(R.id.lista);
        AdapterEstacion adapter = new AdapterEstacion(getActivity(), estaciones);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                Toast.makeText(getActivity(),estaciones.get(position).getTitulo(), Toast.LENGTH_LONG).show();

            }
        });


        return view;
    }

}
