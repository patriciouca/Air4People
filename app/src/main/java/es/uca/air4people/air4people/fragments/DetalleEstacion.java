package es.uca.air4people.air4people.fragments;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Semaphore;

import es.uca.air4people.air4people.ContaminacionHelper;
import es.uca.air4people.air4people.EstacionesActivity;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.ReciclerEstaciones.EstacionLista;
import es.uca.air4people.air4people.ReciclerMedicionFecha.AdaptadorMedicionesFecha;
import es.uca.air4people.air4people.Servicio.EstacionService;
import es.uca.air4people.air4people.Servicio.Medicion;
import es.uca.air4people.air4people.Servicio.Mediciones;
import es.uca.air4people.air4people.memoria.MemoriaAplicacion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleEstacion extends Fragment {

    public static int AVANCEDEFECTO=2;
    private String titulo;
    private EstacionLista estacion;
    private RecyclerView recView;
    private ArrayList<Mediciones> datos;
    private AdaptadorMedicionesFecha adaptador;
    private int dias;



    public DetalleEstacion() {
        EstacionesActivity.setFuera2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.content_detallestacioncam, container, false);

        TextView tituloT=view.findViewById(R.id.tituloEstacion);
        dias=1;
        Bundle bundle=getArguments();
        titulo=bundle.getString("titulo");
        tituloT.setText((String)bundle.getString("titulo"));
        datos=new ArrayList<Mediciones>();

        ArrayList<Medicion> m= (ArrayList<Medicion>) bundle.getSerializable("hoy");


        final EncolarEstacionDia encolarEstacionDia=new EncolarEstacionDia(view,view.getContext());


        ImageView icono1=view.findViewById(R.id.imageView3);
        ImageView icono2=view.findViewById(R.id.imageView4);
        ImageView icono3=view.findViewById(R.id.imageView2);
        ImageView icono4=view.findViewById(R.id.imageView);

        recView = (RecyclerView) view.findViewById(R.id.recEst);
        recView.setHasFixedSize(true);
        adaptador=new AdaptadorMedicionesFecha(datos);
        recView.setAdapter(adaptador);

        recView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        recView.addItemDecoration(
                new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

        recView.setItemAnimator(new DefaultItemAnimator());

        encolarEstacionDia.anadirPPrediccion(m);


        for (int i=0;i<=AVANCEDEFECTO;i++)
        {
            encolarEstacionDia.anadirPrediccion(dias);
            dias++;
        }

        int[] niveles= ContaminacionHelper.getProblemas(m);
        Log.d("Raro","Valor "+String.valueOf(ContaminacionHelper.getValorContaminante("Ozono",datos.get(0).getMediciones())));

        switch (niveles[0])
        {
            case 1:
                icono1.setImageResource(R.drawable.ic_child_friendly_black_24dp_gr);
                break;
            case 2:
                icono1.setImageResource(R.drawable.ic_child_friendly_black_24dp_y);
                break;
            case 3:
                icono1.setImageResource(R.drawable.ic_child_friendly_black_24dp_r);
                break;
            default:
                icono1.setImageResource(R.drawable.ic_child_friendly_black_24dp);
        }
        switch (niveles[1])
        {
            case 1:
                icono2.setImageResource(R.drawable.ic_fitness_center_black_24dp_gr);
                break;
            case 2:
                icono2.setImageResource(R.drawable.ic_fitness_center_black_24dp_y);
                break;
            case 3:
                icono2.setImageResource(R.drawable.ic_fitness_center_black_24dp_r);
                break;
            default:
                icono2.setImageResource(R.drawable.ic_fitness_center_black_24dp);
        }

        switch (niveles[2])
        {
            case 1:
                icono3.setImageResource(R.drawable.ic_directions_run_black_24dp_gr);
                break;
            case 2:
                icono3.setImageResource(R.drawable.ic_directions_run_black_24dp_y);
                break;
            case 3:
                icono3.setImageResource(R.drawable.ic_directions_run_black_24dp_r);
                break;
            default:
                icono3.setImageResource(R.drawable.ic_directions_run_black_24dp);
        }

        switch (niveles[3])
        {
            case 1:
                icono4.setImageResource(R.drawable.ic_grandfather_gr);
                break;
            case 2:
                icono4.setImageResource(R.drawable.ic_grandfather_y);
                break;
            case 3:
                icono4.setImageResource(R.drawable.ic_grandfather_r);
                break;
            default:
                icono4.setImageResource(R.drawable.ic_grandfather);
        }

        recView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {

                    encolarEstacionDia.anadirPrediccion(dias);
                    dias++;
                }
            }
        });

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

        public void anadirPPrediccion(ArrayList<Medicion> m){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final String formattedDate = df.format(c.getTime());
            setLista(new Mediciones(m,formattedDate));
        }



        public void anadirPrediccion(final int dia){

            Bundle bundle = getArguments();
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl("http://airservices.uca.es/Air4People/").
                    addConverterFactory(GsonConverterFactory.create())
                    .build();

            EstacionService estacionService = retrofit.create(EstacionService.class);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -dia);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            final String formattedDate = df.format(c.getTime());
            final String titulo=bundle.getString("titulo");
            int diaParametro=-dia;
            Call<List<Medicion>> call = estacionService.getPrediccionFecha(titulo,formattedDate+"T00:00:00");

            try {

                int j;
                int s;
                Response<List<Medicion>> response=call.execute();

                if(c.get(Calendar.MONTH)!=7)
                {
                    int i=0;
                    while(response.body().size()==0 && i<=24)
                    {
                        String hora=String.format("%02d",i);

                        j=0;

                        while(response.body().size()==0 && j!=60)
                        {
                            String minutos=String.format("%02d",j);
                            call = estacionService.getPrediccionFecha(titulo,formattedDate+"T"+hora+":"+minutos+":00");
                            response=call.execute();
                            j+=10;


                        }
                        //call = estacionService.getPrediccionFecha(titulo,formattedDate+"T"+hora+":"+"00"+":00");
                        i+=2;
                    }
                }

                setDias(diaParametro);
                Mediciones m=new Mediciones(response.body(),formattedDate);
                setLista(m);
            } catch (IOException e) {

            }

        }

        public void setLista() {

            datos.add(estacion.getMediciones().get(estacion.getMediciones().size()-dias));
            adaptador.notifyItemInserted(datos.size());

        }

        public void setLista(Mediciones mediciones) {


            datos.add(mediciones);
            adaptador.notifyItemInserted(datos.size());

        }

        public void setDias(int dias) {
            this.dias = dias;
        }
    }

}
