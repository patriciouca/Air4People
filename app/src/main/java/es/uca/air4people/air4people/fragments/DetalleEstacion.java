package es.uca.air4people.air4people.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.uca.air4people.air4people.ComprobarContaminacion;
import es.uca.air4people.air4people.EstacionesActivity;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.Servicio.Prediccion;

public class DetalleEstacion extends Fragment {

    private String titulo;
    private List<Prediccion> prediccion;

    public DetalleEstacion() {
        EstacionesActivity.setFuera2();
    }

    public void setLista(List<Prediccion> prediccion){
        this.prediccion=prediccion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.content_detallestacion, container, false);
        TextView titulo=view.findViewById(R.id.tituloEstacion);
        Bundle bundle=getArguments();
        titulo.setText((String)bundle.getString("titulo"));
        LinearLayout adjuntar = view.findViewById(R.id.adjuntarPredi);
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        TextView fecha = new TextView(this.getContext());
        fecha.setTextColor(Color.BLACK);
        fecha.setText(formattedDate);
        adjuntar.addView(fecha);

        for (Prediccion a:prediccion) {
            View hijo = getLayoutInflater().inflate(R.layout.prediccionvertical, null);
            ConstraintLayout vertical = view.findViewById(R.id.prediccionvertical);

            TextView tmote = hijo.findViewById(R.id.tvT);
            tmote.setText(a.getDes_kind());

            TextView valor = hijo.findViewById(R.id.tvV);
            valor.setText(String.valueOf(a.getValue()));
            ProgressBar barra = hijo.findViewById(R.id.pB);
            ImageButton botoncito = hijo.findViewById(R.id.btInf);
            int comprobacion= ComprobarContaminacion.comprobar(a.getDes_kind(),a.getValue());

            switch (comprobacion){
                case 1:
                    barra.setProgress(25);
                    barra.getProgressDrawable().setColorFilter(
                            ComprobarContaminacion.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                    barra.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        botoncito.setTooltipText(getString(R.string.x1));
                    }
                    break;
                case 2:
                    barra.setProgress(50);
                    barra.getProgressDrawable().setColorFilter(
                            ComprobarContaminacion.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                    barra.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        if(a.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS"))
                            botoncito.setTooltipText(getString(R.string.pm102));
                        else if(a.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
                            botoncito.setTooltipText(getString(R.string.co2));
                        else
                            botoncito.setVisibility(View.GONE);
                    }
                    break;
                case 3:
                    barra.setProgress(75);
                    barra.getProgressDrawable().setColorFilter(
                            ComprobarContaminacion.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                    barra.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        if(a.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS"))
                            botoncito.setTooltipText(getString(R.string.pm103));
                        else if(a.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
                            botoncito.setTooltipText(getString(R.string.co3));
                        else
                            botoncito.setVisibility(View.GONE);
                    }
                    break;
                case 4:
                    barra.getProgressDrawable().setColorFilter(
                            ComprobarContaminacion.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                    barra.setProgress(100);
                    barra.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        if(a.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS"))
                            botoncito.setTooltipText(getString(R.string.pm104));
                        else if(a.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
                            botoncito.setTooltipText(getString(R.string.co4));
                        else
                            botoncito.setVisibility(View.GONE);
                    }
                    break;
                default:
                    botoncito.setVisibility(View.GONE);
                    break;
            }
            adjuntar.addView(hijo);
        }
        return view;
    }


}
