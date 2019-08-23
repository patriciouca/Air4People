package es.uca.air4people.air4people.unitarias;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.uca.air4people.air4people.Servicio.Estacion;
import es.uca.air4people.air4people.Servicio.EstacionService;
import es.uca.air4people.air4people.Servicio.Medicion;
import es.uca.air4people.air4people.recicler.ReciclerSuscripciones.Suscripcion;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioTest {

    @Test
    public void getMapa() {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://airservices.uca.es/Air4People/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        EstacionService estacionService = retrofit.create(EstacionService.class);
        Call<List<Estacion>> call = estacionService.getMapa();
        try {
            Response<List<Estacion>> response=call.execute();
            /*Conexion OK*/
            assertTrue(response.isSuccessful());
            ArrayList<Estacion> estaciones= (ArrayList<Estacion>) response.body();
            /*Mas que 0*/
            assertTrue(estaciones.size()>0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getContaminantes() {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://airservices.uca.es/Air4People/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        EstacionService estacionService = retrofit.create(EstacionService.class);
        Call<List<String>> call = estacionService.getContaminantes();
        try {
            Response<List<String>> response=call.execute();
            /*Conexion OK*/
            assertTrue(response.isSuccessful());
            ArrayList<String> estaciones= (ArrayList<String>) response.body();
            /*Mas que 0*/
            assertTrue(estaciones.size()>0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getPredicciones() {

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://airservices.uca.es/Air4People/").
                addConverterFactory(GsonConverterFactory.create())
                .build();

        EstacionService estacionService = retrofit.create(EstacionService.class);
        Call<List<Estacion>> call = estacionService.getMapa();
        try {
            Response<List<Estacion>> response=call.execute();
            Estacion primeraEstacion=response.body().get(0);

            Call<List<Medicion>> call2 = estacionService.getPredicciones(primeraEstacion.getMote_name());
            try {
                Response<List<Medicion>> response2=call2.execute();
                /*Conexion OK*/
                assertTrue(response2.isSuccessful());
                ArrayList<Medicion> mediciones= (ArrayList<Medicion>) response2.body();
                /*Mas que 0*/
                assertTrue(mediciones.size()>0);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}