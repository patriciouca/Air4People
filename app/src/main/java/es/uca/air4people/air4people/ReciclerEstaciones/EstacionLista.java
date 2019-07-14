package es.uca.air4people.air4people.ReciclerEstaciones;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.uca.air4people.air4people.Servicio.Medicion;
import es.uca.air4people.air4people.Servicio.Mediciones;

public class EstacionLista {

    private String titulo;
    private int id;
    private ArrayList<Mediciones> mediciones;

    public EstacionLista(String titulo) {
        super();
        this.titulo = titulo;
    }

    public EstacionLista(int id,String titulo) {
        this.titulo = titulo;
        this.id=id;
    }

    public EstacionLista(int id,String titulo, List<Medicion> mediciones) {
        super();
        this.titulo = titulo;
        this.id=id;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate = df.format(c.getTime());
        this.mediciones=new ArrayList<>();
        this.addMedicion(new Mediciones(mediciones,formattedDate));
    }

    public EstacionLista(String titulo, List<Medicion> mediciones) {
        super();
        this.titulo = titulo;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate = df.format(c.getTime());
        this.mediciones=new ArrayList<>();
        this.addMedicion(new Mediciones(mediciones,formattedDate));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addMedicion(Mediciones e){
        mediciones.add(e);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ArrayList<Mediciones> getMediciones() {
        return mediciones;
    }

    public void setMediciones(ArrayList<Mediciones> mediciones) {
        this.mediciones = mediciones;
    }
}