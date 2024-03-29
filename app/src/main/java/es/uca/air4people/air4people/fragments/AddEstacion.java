package es.uca.air4people.air4people.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.ContaminacionHelper;
import es.uca.air4people.air4people.EstacionesActivity;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.recicler.ReciclerEstaciones.AdaptadorEstacion;
import es.uca.air4people.air4people.Servicio.Estacion;
import es.uca.air4people.air4people.Servicio.EstacionService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddEstacion extends Fragment {

    ArrayList<Estacion> lista;

    public AddEstacion() {
        EstacionesActivity.setFuera2();
    }

    public void onBackPressed()
    {
        Fragment anadir=new ListaMisEstaciones();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, anadir)
                .commit();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Inicio");
        EstacionesActivity.setTitulo("Inicio");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View vista=inflater.inflate(R.layout.content_addestacion, container, false);
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://airservices.uca.es/Air4People/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        final RecyclerView rec=vista.findViewById(R.id.addestacion);
        EstacionService estacionService = retrofit.create(EstacionService.class);
        Call<List<Estacion>> call = estacionService.getMapa();
        call.enqueue(new Callback<List<Estacion>>() {
            @Override
            public void onResponse(Call<List<Estacion>> call, final Response<List<Estacion>> response) {

                Collections.sort(response.body(), new Comparator<Estacion>()
                {
                    @Override
                    public int compare(Estacion es1, Estacion es2)
                    {
                        return es1.getMote_name().compareToIgnoreCase(es2.getMote_name());
                    }
                });

                rec.setHasFixedSize(true);
                //Collections.sort(response.body());
                lista=new ArrayList<>(response.body());
                AndroidBaseDatos baseDatos=new AndroidBaseDatos(getContext());

                for(String estacion:baseDatos.getEstaciones()){
                    lista=ContaminacionHelper.quitarEstacion(lista,estacion);
                }
                AdaptadorEstacion adaptador=new AdaptadorEstacion(lista);
                rec.setAdapter(adaptador);

                rec.setLayoutManager(
                        new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));


                final GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                    @Override public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });

                rec.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean b) {

                    }

                    @Override
                    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                        try {
                            View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                            if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

                                int position = recyclerView.getChildAdapterPosition(child);
                                AndroidBaseDatos baseDatos=new AndroidBaseDatos(getContext());
                                baseDatos.addEstacion(lista.get(position));
                                onBackPressed();
                                return true;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        return false;
                    }

                    @Override
                    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

                    }
                });


                rec.setItemAnimator(new DefaultItemAnimator());

                rec.addItemDecoration(
                        new DividerItemDecoration(vista.getContext(), DividerItemDecoration.VERTICAL));

            }

            @Override
            public void onFailure(Call<List<Estacion>> call, Throwable t) {

                Log.d("RARO",t.getMessage());
            }
        });
        return vista;
    }

    public interface RecyclerViewOnItemClickListener {

        void onClick(View v, int position);
    }
}