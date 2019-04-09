package es.uca.air4people.air4people.ReciclerEstaciones;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import es.uca.air4people.air4people.Servicio.Medicion;
import es.uca.air4people.air4people.Servicio.Mediciones;

public class EstacionLista {

    private String titulo;
    private Mediciones mediciones;

    public EstacionLista(String titulo) {
        super();
        this.titulo = titulo;
    }

    public EstacionLista(String titulo, List<Medicion> mediciones) {
        super();
        this.titulo = titulo;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate = df.format(c.getTime());
        this.mediciones = new Mediciones(mediciones,formattedDate);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Mediciones getMediciones() {
        return mediciones;
    }

    public void setMediciones(Mediciones mediciones) {
        this.mediciones = mediciones;
    }
}