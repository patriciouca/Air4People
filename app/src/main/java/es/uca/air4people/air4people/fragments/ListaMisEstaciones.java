package es.uca.air4people.air4people.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.EstacionesActivity;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.recicler.ReciclerEstaciones.AdaptadorEstacionesMediciones;
import es.uca.air4people.air4people.Servicio.Estacion;
import es.uca.air4people.air4people.Servicio.EstacionService;
import es.uca.air4people.air4people.Servicio.Medicion;
import es.uca.air4people.air4people.recicler.ReciclerEstaciones.EstacionLista;
import es.uca.air4people.air4people.memoria.MemoriaAplicacion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaMisEstaciones extends Fragment {

    static public boolean permitido;

    public ListaMisEstaciones() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListaMisEstaciones.permitido=true;
        View view = inflater.inflate(R.layout.content_listaestaciones, container, false);

        final ArrayList<EstacionLista> estaciones = new ArrayList<EstacionLista>();
        EncolarEstacion encolarEstacion=new EncolarEstacion(estaciones,view);

        EstacionesActivity.setContexto(view.getContext());
        AndroidBaseDatos baseDatos=new AndroidBaseDatos(getContext());
        ((MemoriaAplicacion) getActivity().getApplication()).setBase(baseDatos);
        baseDatos.getNotificaciones();
        ArrayList<Estacion> estacionesS=baseDatos.getEstacionesE();
        for (Estacion e : estacionesS) {
            EstacionLista lista=((MemoriaAplicacion) this.getActivity().getApplication()).getEstacion(e.getMote_name());

            if(lista==null)
                encolarEstacion.anadirEstacion(e.getMote_name(),e.getMote_id());
            else
            {
                estaciones.add(lista);
                encolarEstacion.anadirEstacionVista(lista.getTitulo(),lista);
            }
        }

        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ListaMisEstaciones.permitido)
                {
                    Fragment anadir=new AddEstacion();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, anadir)
                            .commit();
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Añadir estación");
                    EstacionesActivity.setTitulo("Añadir estación");
                }

            }
        });

        return view;
    }

    class EncolarEstacion{
        private ArrayList<EstacionLista> estaciones = new ArrayList<EstacionLista>();
        private RecyclerView rec;
        private View v;

        public EncolarEstacion(ArrayList<EstacionLista> estaciones,View v) {
            this.estaciones = estaciones;
            this.v=v;
        }

        public void anadirEstacionVista(String nombre,EstacionLista estacion){
            rec=v.findViewById(R.id.rec);
            rec.setHasFixedSize(true);

            final AdaptadorEstacionesMediciones adaptador = new AdaptadorEstacionesMediciones(estaciones,getActivity());

            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("IMPORTANTE",String.valueOf(ListaMisEstaciones.permitido));

                    if(ListaMisEstaciones.permitido) {
                        final int position = rec.getChildAdapterPosition(v);
                        Fragment fragment = new DetalleEstacion();
                        Bundle bundle = new Bundle();

                        Log.d("RARO", "ESTA" + String.valueOf(estaciones.get(position).getHoy().size()));
                        bundle.putString("titulo", estaciones.get(position).getTitulo());
                        bundle.putSerializable("hoy", estaciones.get(position).getHoy());

                        fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, fragment).addToBackStack("T")
                                .commit();
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(estaciones.get(position).getTitulo());
                        EstacionesActivity.setTitulo(estaciones.get(position).getTitulo());
                    }
                }
            });

            rec.setAdapter(adaptador);
            rec.setLayoutManager(
                    new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
            rec.addItemDecoration(
                    new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
            rec.setItemAnimator(new DefaultItemAnimator());
        }

        public void anadirEstacion(final String nombre,final int id){
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl("http://airservices.uca.es/Air4People/").
                    addConverterFactory(GsonConverterFactory.create())
                    .build();

            final EstacionService estacionService = retrofit.create(EstacionService.class);
            Call<List<Medicion>> call = estacionService.getPredicciones(nombre);
            call.enqueue(new Callback<List<Medicion>>() {
                @Override
                public void onResponse(Call<List<Medicion>> call, final Response<List<Medicion>> response) {

                    EstacionLista estl=new EstacionLista(id,nombre, response.body());
                    ((MemoriaAplicacion) getActivity().getApplication()).setEstacion(nombre,estl);

                    estl.setHoy(new ArrayList<Medicion>(response.body()));
                    estaciones.add(estl);

                    anadirEstacionVista(nombre,new EstacionLista(id,nombre,response.body()));

                }

                @Override
                public void onFailure(Call<List<Medicion>> call, Throwable t) {

                }
            });
        }
    }

}
