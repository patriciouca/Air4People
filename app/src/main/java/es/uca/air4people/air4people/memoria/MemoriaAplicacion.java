package es.uca.air4people.air4people.memoria;

import android.app.Application;

import java.util.Hashtable;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.recicler.ReciclerEstaciones.EstacionLista;
import es.uca.air4people.air4people.Servicio.Mediciones;

public class MemoriaAplicacion extends Application {

    static Hashtable<String, EstacionLista> estaciones = new Hashtable<String, EstacionLista>();

    static AndroidBaseDatos base;

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

    public static AndroidBaseDatos getBase() {
        return base;
    }

    public static void setBase(AndroidBaseDatos base) {
        MemoriaAplicacion.base = base;
    }
}
