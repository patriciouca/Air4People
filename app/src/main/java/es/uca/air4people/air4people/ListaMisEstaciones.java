package es.uca.air4people.air4people;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import es.uca.air4people.air4people.lista.AdapterEstacion;
import es.uca.air4people.air4people.lista.EstacionLista;

public class ListaMisEstaciones extends Fragment {

    public ListaMisEstaciones() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.content_listaestaciones, container, false);
    }
}
