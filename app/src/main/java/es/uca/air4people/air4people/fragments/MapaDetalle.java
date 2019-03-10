package es.uca.air4people.air4people.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.uca.air4people.air4people.R;

public class MapaDetalle  extends Fragment{
    public MapaDetalle() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_detallemapa, container, false);
    }
}
