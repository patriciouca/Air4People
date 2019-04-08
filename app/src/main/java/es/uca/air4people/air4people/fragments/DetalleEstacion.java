package es.uca.air4people.air4people.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.uca.air4people.air4people.EstacionesActivity;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.ReciclerMedicionFecha.AdaptadorMedicionesFecha;
import es.uca.air4people.air4people.Servicio.EstacionService;
import es.uca.air4people.air4people.Servicio.Medicion;
import es.uca.air4people.air4people.Servicio.Mediciones;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleEstacion extends Fragment {

    public static int AVANCEDEFECTO=2;
    private String titulo;
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
        dias=0;
        Bundle bundle=getArguments();
        titulo=bundle.getString("titulo");
        tituloT.setText((String)bundle.getString("titulo"));
        datos=new ArrayList<Mediciones>();
        final EncolarEstacionDia encolarEstacionDia=new EncolarEstacionDia(view,view.getContext());

        recView = (RecyclerView) view.findViewById(R.id.recEst);
        recView.setHasFixedSize(true);
        adaptador=new AdaptadorMedicionesFecha(datos);
        recView.setAdapter(adaptador);

        recView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        recView.addItemDecoration(
                new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

        recView.setItemAnimator(new DefaultItemAnimator());

        for (int i=0;i<=AVANCEDEFECTO;i++)
        {
            encolarEstacionDia.anadirPrediccion(dias);
            dias++;
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
            final String formattedDate = df.format(c.getTime());

            final String titulo=bundle.getString("titulo");
            final int diaParametro=-dia;
            Call<List<Medicion>> call = estacionService.getPrediccionFecha(titulo,formattedDate);
            call.enqueue(new Callback<List<Medicion>>() {
                @Override
                public void onResponse(Call<List<Medicion>> call, final Response<List<Medicion>> response) {
                    setDias(diaParametro);
                    setLista(new Mediciones(response.body(),formattedDate));
                }

                @Override
                public void onFailure(Call<List<Medicion>> call, Throwable t) {
                }
            });
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
