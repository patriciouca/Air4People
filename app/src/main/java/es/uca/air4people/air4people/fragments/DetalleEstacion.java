package es.uca.air4people.air4people.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.uca.air4people.air4people.ComprobarContaminacion;
import es.uca.air4people.air4people.EstacionesActivity;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.Servicio.EstacionService;
import es.uca.air4people.air4people.Servicio.Medicion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleEstacion extends Fragment {

    public static int AVANCEDEFECTO=2;
    private String titulo;
    private List<Medicion> medicion;

    public DetalleEstacion() {
        EstacionesActivity.setFuera2();
    }

    public void setLista(List<Medicion> medicion){
        this.medicion = medicion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.content_detallestacion, container, false);

        TextView tituloT=view.findViewById(R.id.tituloEstacion);

        Bundle bundle=getArguments();
        titulo=bundle.getString("titulo");
        tituloT.setText((String)bundle.getString("titulo"));

        LinearLayout adjuntar = view.findViewById(R.id.adjuntarPredi);
        Date c = Calendar.getInstance().getTime();

        EncolarEstacionDia encolarEstacionDia=new EncolarEstacionDia(view,view.getContext());
        GestosDetalle g=new GestosDetalle(bundle.getString("titulo"),encolarEstacionDia, (ScrollView) view.findViewById(R.id.scroll));
        final GestureDetector gesture = new GestureDetector(getActivity(),g);

        view.findViewById(R.id.scroll).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

        for (int i=0;i<=AVANCEDEFECTO-1;i++)
        {
            encolarEstacionDia.anadirPrediccion(i);
        }


        return view;
    }

    class EncolarEstacionDia {

        View view;
        Context contexto;
        List<Medicion> lista;
        int dias;

        public EncolarEstacionDia(View view, Context contexto) {
            this.view = view;
            this.contexto = contexto;
        }

        public void anadirPrediccion(int dia){

            Bundle bundle = getArguments();
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl("http://airservices.uca.es/Air4People/").
                    addConverterFactory(GsonConverterFactory.create())
                    .build();
            EstacionService estacionService = retrofit.create(EstacionService.class);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -dia);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());

            final String titulo=bundle.getString("titulo");
            final int diaParametro=-dia;
            Call<List<Medicion>> call = estacionService.getPrediccionFecha(titulo,formattedDate);
            call.enqueue(new Callback<List<Medicion>>() {
                @Override
                public void onResponse(Call<List<Medicion>> call, final Response<List<Medicion>> response) {
                    setDias(diaParametro);
                    setLista(response.body());
                    anadirPrediccion();
                }

                @Override
                public void onFailure(Call<List<Medicion>> call, Throwable t) {
                }
            });
        }

        public void setLista(List<Medicion> medicion) {
            this.lista = medicion;
        }

        public void setDias(int dias) {
            this.dias = dias;
        }

        public void anadirPrediccion() {

            TextView titulo = view.findViewById(R.id.tituloEstacion);
            Bundle bundle = getArguments();
            titulo.setText((String) bundle.getString("titulo"));

            LinearLayout adjuntar = view.findViewById(R.id.adjuntarPredi);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, dias);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());
            TextView fecha = new TextView(contexto);
            fecha.setTextColor(Color.BLACK);
            fecha.setText(formattedDate);

            adjuntar.addView(fecha);

            for (Medicion a : lista) {
                View hijo = getLayoutInflater().inflate(R.layout.prediccionvertical, null);
                ConstraintLayout vertical = view.findViewById(R.id.prediccionvertical);

                TextView tmote = hijo.findViewById(R.id.tvT);
                tmote.setText(a.getDes_kind());

                TextView valor = hijo.findViewById(R.id.tvV);
                valor.setText(String.valueOf(a.getValue()));
                ProgressBar barra = hijo.findViewById(R.id.pB);
                ImageButton botoncito = hijo.findViewById(R.id.btInf);
                int comprobacion = ComprobarContaminacion.comprobar(a.getDes_kind(), a.getValue());

                switch (comprobacion) {
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
                            if (a.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS"))
                                botoncito.setTooltipText(getString(R.string.pm102));
                            else if (a.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
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
                            if (a.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS"))
                                botoncito.setTooltipText(getString(R.string.pm103));
                            else if (a.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
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
                            if (a.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS"))
                                botoncito.setTooltipText(getString(R.string.pm104));
                            else if (a.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
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
    }

}
