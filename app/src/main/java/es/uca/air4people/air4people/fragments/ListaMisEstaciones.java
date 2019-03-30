package es.uca.air4people.air4people.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.uca.air4people.air4people.ComprobarContaminacion;
import es.uca.air4people.air4people.EstacionesActivity;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.Servicio.EstacionService;
import es.uca.air4people.air4people.Servicio.Prediccion;
import es.uca.air4people.air4people.lista.AdapterEstacion;
import es.uca.air4people.air4people.lista.EstacionLista;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaMisEstaciones extends Fragment {

    public ListaMisEstaciones() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_listaestaciones, container, false);

        final ArrayList<EstacionLista> estaciones = new ArrayList<EstacionLista>();
        EncolarEstacion encolarEstacion=new EncolarEstacion(estaciones);
        encolarEstacion.anadirEstacion("SanFernando");
        encolarEstacion.anadirEstacion("Centro");


        return view;
    }

    class EncolarEstacion{
        private ArrayList<EstacionLista> estaciones = new ArrayList<EstacionLista>();

        public EncolarEstacion(ArrayList<EstacionLista> estaciones) {
            this.estaciones = estaciones;
        }

        public void anadirEstacion(final String nombre){
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl("http://airservices.uca.es/Air4People/").
                    addConverterFactory(GsonConverterFactory.create())
                    .build();

            EstacionService estacionService = retrofit.create(EstacionService.class);
            Call<List<Prediccion>> call = estacionService.getPredicciones(nombre);
            final String titulo=nombre;
            call.enqueue(new Callback<List<Prediccion>>() {
                @Override
                public void onResponse(Call<List<Prediccion>> call, final Response<List<Prediccion>> response) {


                    estaciones.add(new EstacionLista(titulo, response.body()));
                    ListView lv = (ListView) getView().findViewById(R.id.lista);
                    AdapterEstacion adapter = new AdapterEstacion(getActivity(), estaciones);
                    lv.setAdapter(adapter);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final int pos = position;
                            Fragment fragment = new DetalleEstacion();
                            Bundle bundle = new Bundle();
                            bundle.putString("titulo",estaciones.get(position).getTitulo());
                            ((DetalleEstacion) fragment).setLista(estaciones.get(position).getPredicciones());
                            fragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment).addToBackStack("T")
                                    .commit();

                        }
                    });
                }

                @Override
                public void onFailure(Call<List<Prediccion>> call, Throwable t) {

                }
            });
        }
    }

}
