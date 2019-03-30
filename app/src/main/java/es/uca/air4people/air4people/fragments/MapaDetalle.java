package es.uca.air4people.air4people.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.provider.FontRequest;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import es.uca.air4people.air4people.ComprobarContaminacion;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.Servicio.EstacionService;
import es.uca.air4people.air4people.Servicio.Prediccion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapaDetalle  extends Fragment  {

    TextView titulo;
    String texto;
    View general;
    ConstraintLayout reglas;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.content_detallemapa, container, false);

        general=view;
        final GestureDetector gesture = new GestureDetector(getActivity(),new GestosMapas((ConstraintLayout) reglas));

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titulo=view.findViewById(R.id.titulo);
        Bundle bundle = this.getArguments();
        texto=bundle.getString("titulo");
        titulo.setText(texto);

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://airservices.uca.es/Air4People/").
                addConverterFactory(GsonConverterFactory.create())
                .build();

        EstacionService estacionService = retrofit.create(EstacionService.class);
        Call<List<Prediccion>> call = estacionService.getPredicciones(texto);
        call.enqueue(new Callback<List<Prediccion>>() {
            @Override
            public void onResponse(Call<List<Prediccion>> call, Response<List<Prediccion>> response) {
                int i=0;
                for (Prediccion a:response.body()){

                    ConstraintLayout vertical=view.findViewById(R.id.prediccionvertical);

                    LinearLayout adjuntar=view.findViewById(R.id.adjuntar);
                    View hijo = getLayoutInflater().inflate(R.layout.prediccionvertical, null);
                    TextView tmote=hijo.findViewById(R.id.tvT);
                    tmote.setText(a.getDes_kind());

                    TextView valor=hijo.findViewById(R.id.tvV);
                    valor.setText(String.valueOf(a.getValue()));
                    ProgressBar barra=hijo.findViewById(R.id.pB);
                    ImageButton botoncito=hijo.findViewById(R.id.btInf);
                    barra.setVisibility(View.INVISIBLE);

                    int comprobacion=ComprobarContaminacion.comprobar(a.getDes_kind(),a.getValue());

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
            }
            @Override
            public void onFailure(Call<List<Prediccion>> call, Throwable t) {

            }
        });

    }

    public ConstraintLayout getReglas() {
        return reglas;
    }

    public void setReglas(ConstraintLayout reglas) {
        this.reglas = reglas;
    }

}