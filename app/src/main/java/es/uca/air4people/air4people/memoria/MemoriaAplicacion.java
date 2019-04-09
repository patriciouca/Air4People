package es.uca.air4people.air4people.memoria;

import android.app.Application;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import es.uca.air4people.air4people.ReciclerEstaciones.EstacionLista;
import es.uca.air4people.air4people.Servicio.Estacion;
import es.uca.air4people.air4people.Servicio.Medicion;
import es.uca.air4people.air4people.Servicio.Mediciones;

public class MemoriaAplicacion extends Application {

    static Hashtable<String, EstacionLista> estaciones = new Hashtable<String, EstacionLista>();

    static Hashtable<EstacionFecha, Mediciones> prediccionFechaestacion = new Hashtable<EstacionFecha,Mediciones>();

    class EstacionFecha{
        public String fecha;
        public String estacion;

        public EstacionFecha(String fecha, String estacion) {
            this.fecha = fecha;
            this.estacion = estacion;
        }
    }

    public static void setEstacion(String nombre,EstacionLista lista){
        estaciones.put(nombre,lista);
    }

    public static EstacionLista getEstacion(String nombre){
        return estaciones.get(nombre);
    }
}
