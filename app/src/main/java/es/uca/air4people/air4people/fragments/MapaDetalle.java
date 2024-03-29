package es.uca.air4people.air4people.fragments;

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
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.ContaminacionHelper;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.Servicio.Estacion;
import es.uca.air4people.air4people.Servicio.EstacionService;
import es.uca.air4people.air4people.Servicio.Medicion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapaDetalle  extends Fragment  {

    TextView titulo;
    String texto;
    String id;
    View general;
    ConstraintLayout reglas;
    GestosMapas mapas;
    Switch suscrito;
    ArrayList<Estacion> estacionesMias;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.content_detallemapa, container, false);

        general=view;
        mapas=new GestosMapas((ConstraintLayout) reglas);
        final GestureDetector gesture = new GestureDetector(getActivity(),mapas);
        suscrito=view.findViewById(R.id.suscrito);
        suscrito.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AndroidBaseDatos baseDatos=new AndroidBaseDatos(getContext());


                if (isChecked) {
                    if (!ContaminacionHelper.estaEstacion(estacionesMias,texto))
                        baseDatos.addEstacion(new Estacion(Integer.valueOf(id),texto));
                } else {
                    baseDatos.deleteEstacion(new Estacion(Integer.valueOf(id),texto));
                }

            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
        AndroidBaseDatos baseDatos=new AndroidBaseDatos(getContext());
        estacionesMias=baseDatos.getEstacionesE();
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titulo=view.findViewById(R.id.titulo);
        suscrito=view.findViewById(R.id.suscrito);

        Bundle bundle = this.getArguments();
        texto=bundle.getString("titulo");
        id=bundle.getString("id");
        titulo.setText(texto);
        
        if(ContaminacionHelper.estaEstacion(estacionesMias,texto))
            suscrito.setChecked(true);

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://airservices.uca.es/Air4People/").
                addConverterFactory(GsonConverterFactory.create())
                .build();

        final EstacionService estacionService = retrofit.create(EstacionService.class);
        Call<List<Medicion>> call = estacionService.getPredicciones(texto);
        call.enqueue(new Callback<List<Medicion>>() {
            @Override
            public void onResponse(Call<List<Medicion>> call, Response<List<Medicion>> response) {
                int i=0;
                for (Medicion a:response.body()){

                    ConstraintLayout vertical=view.findViewById(R.id.prediccionvertical);

                    LinearLayout adjuntar=view.findViewById(R.id.adjuntar);
                    try{
                        View hijo = getLayoutInflater().inflate(R.layout.prediccionvertical, null);
                        TextView tmote=hijo.findViewById(R.id.tvT);
                        tmote.setText(a.getDes_kind());

                        TextView valor=hijo.findViewById(R.id.tvV);
                        valor.setText(String.valueOf(a.getValue())+" "+a.getUnit());
                        ProgressBar barra=hijo.findViewById(R.id.pB);
                        ImageButton botoncito=hijo.findViewById(R.id.btInf);
                        barra.setVisibility(View.INVISIBLE);

                        int comprobacion= ContaminacionHelper.comprobar(a.getDes_kind(),a.getValue());

                        switch (comprobacion){
                            case 1:
                                barra.setProgress(25);
                                barra.getProgressDrawable().setColorFilter(
                                        ContaminacionHelper.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                                barra.setVisibility(View.VISIBLE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    botoncito.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Toast.makeText(view.getContext(), getString(R.string.x1), Toast.LENGTH_LONG).show();

                                        }
                                    });
                                }
                                break;
                            case 2:
                                barra.setProgress(50);
                                barra.getProgressDrawable().setColorFilter(
                                        ContaminacionHelper.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                                barra.setVisibility(View.VISIBLE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    if(a.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.pm102), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(a.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.co2), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(a.getDes_kind().toUpperCase().equals("OZONO"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.o32), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(a.getDes_kind().toUpperCase().equals("DIOXIDO DE AZUFRE"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.so22), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(a.getDes_kind().toUpperCase().equals("DIOXIDO DE NITROGENO"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.no22), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else
                                        botoncito.setVisibility(View.GONE);
                                }
                                break;
                            case 3:
                                barra.setProgress(75);
                                barra.getProgressDrawable().setColorFilter(
                                        ContaminacionHelper.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                                barra.setVisibility(View.VISIBLE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    if(a.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS")){

                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.pm103), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(a.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
                                    {

                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.co3), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(a.getDes_kind().toUpperCase().equals("OZONO"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.o33), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(a.getDes_kind().toUpperCase().equals("DIOXIDO DE AZUFRE"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.so23), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(a.getDes_kind().toUpperCase().equals("DIOXIDO DE NITROGENO"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.no23), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else
                                        botoncito.setVisibility(View.GONE);
                                }
                                break;
                            case 4:
                                barra.getProgressDrawable().setColorFilter(
                                        ContaminacionHelper.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                                barra.setProgress(100);
                                barra.setVisibility(View.VISIBLE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    if(a.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.pm104), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(a.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.co4), Toast.LENGTH_LONG).show();

                                            }
                                        });

                                    }
                                    else if(a.getDes_kind().toUpperCase().equals("OZONO"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.o34), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(a.getDes_kind().toUpperCase().equals("DIOXIDO DE AZUFRE"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.so24), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(a.getDes_kind().toUpperCase().equals("DIOXIDO DE NITROGENO"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), getString(R.string.no24), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else
                                    {
                                        botoncito.setVisibility(View.GONE);
                                    }
                                }
                                break;
                            default:
                                botoncito.setVisibility(View.GONE);
                                break;
                        }
                        adjuntar.addView(hijo);

                    }catch (Exception e)
                    {

                    }

                }
            }
            @Override
            public void onFailure(Call<List<Medicion>> call, Throwable t) {

            }
        });

    }

    public ConstraintLayout getReglas() {
        return reglas;
    }

    public void setReglas(ConstraintLayout reglas) {
        this.reglas = reglas;
    }

    public void guardarVentana(){
        mapas.abajo();
    }

}
