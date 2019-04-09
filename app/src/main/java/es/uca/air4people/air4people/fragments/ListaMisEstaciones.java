package es.uca.air4people.air4people.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.ReciclerEstaciones.AdaptadorEstacionesMediciones;
import es.uca.air4people.air4people.Servicio.EstacionService;
import es.uca.air4people.air4people.Servicio.Medicion;
import es.uca.air4people.air4people.ReciclerEstaciones.EstacionLista;
import es.uca.air4people.air4people.memoria.MemoriaAplicacion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaMisEstaciones extends Fragment {

    String misestaciones[]={"SanFernando","Centro","Mediterraneo"};
    public ListaMisEstaciones() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_listaestaciones, container, false);

        final ArrayList<EstacionLista> estaciones = new ArrayList<EstacionLista>();
        EncolarEstacion encolarEstacion=new EncolarEstacion(estaciones,view);

        for(String estacion:misestaciones){
            EstacionLista lista=((MemoriaAplicacion) this.getActivity().getApplication()).getEstacion(estacion);
            if(lista==null)
                encolarEstacion.anadirEstacion(estacion);
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

                Fragment anadir=new AddEstacion();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, anadir)
                        .commit();
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Añadir estacion");
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

            final AdaptadorEstacionesMediciones adaptador = new AdaptadorEstacionesMediciones(estaciones);

            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = rec.getChildAdapterPosition(v);
                    Fragment fragment = new DetalleEstacion();
                    Bundle bundle = new Bundle();
                    bundle.putString("titulo",estaciones.get(position).getTitulo());
                    //((DetalleEstacion) fragment).setLista(estaciones.get(position).getMediciones());
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, fragment).addToBackStack("T")
                            .commit();
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(estaciones.get(position).getTitulo());

                }
            });
            rec.setAdapter(adaptador);
            rec.setLayoutManager(
                    new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
            rec.addItemDecoration(
                    new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
            rec.setItemAnimator(new DefaultItemAnimator());

        }

        public void anadirEstacion(final String nombre){
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl("http://airservices.uca.es/Air4People/").
                    addConverterFactory(GsonConverterFactory.create())
                    .build();

            EstacionService estacionService = retrofit.create(EstacionService.class);
            Call<List<Medicion>> call = estacionService.getPredicciones(nombre);
            final String titulo=nombre;
            call.enqueue(new Callback<List<Medicion>>() {
                @Override
                public void onResponse(Call<List<Medicion>> call, final Response<List<Medicion>> response) {

                    ((MemoriaAplicacion) getActivity().getApplication()).setEstacion(titulo,new EstacionLista(titulo, response.body()));
                    estaciones.add(new EstacionLista(titulo, response.body()));
                    anadirEstacionVista(titulo,new EstacionLista(titulo,response.body()));

                }

                @Override
                public void onFailure(Call<List<Medicion>> call, Throwable t) {

                }
            });
        }
    }

}
