package es.uca.air4people.air4people.fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import es.uca.air4people.air4people.R;

public class MapaDetalle  extends Fragment {

    ConstraintLayout reglas;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.content_detallemapa, container, false);
        Log.d("RARO",reglas.toString());

        final GestureDetector gesture = new GestureDetector(getActivity(),new GestosMapas((ConstraintLayout) reglas));

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
        return view;
    }

    public ConstraintLayout getReglas() {
        return reglas;
    }

    public void setReglas(ConstraintLayout reglas) {
        this.reglas = reglas;
    }
}
